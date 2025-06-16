let editingProductId = null;

const pageSize = 20;
let currentPage = 1;
let totalPages = 0;

document.addEventListener('DOMContentLoaded', () => {
    loadProducts(currentPage);
});

function loadProducts(pageNumber) {
    if (totalPages > 0) {
        pageNumber = Math.max(1, Math.min(pageNumber, totalPages));
    }
    fetch(`/products?pageNumber=${pageNumber}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(data => {
            const { productResponseList, pageNumber: serverPage, totalElements } = data.data;
            currentPage = serverPage;
            totalPages = Math.ceil(totalElements / pageSize);

            renderTable(productResponseList);
            renderPagination();
        })
        .catch(() => alert('상품 데이터를 불러오는 중 오류 발생'));
}

function renderTable(list) {
    const tbody = document.getElementById('productTableBody');
    tbody.innerHTML = '';
    list.forEach(p => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.productType}</td>
            <td>${p.productCategory}</td>
            <td>${p.description}</td>
            <td>${p.productImage}</td>
            <td>
                <button class="btn btn-primary" onclick="editProduct(${p.id})">수정</button>
                <button class="btn btn-ghost" onclick="deleteProduct(${p.id})">삭제</button>
            </td>`;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById('pagination');
    container.innerHTML = '';

    const prev = document.createElement('button');
    prev.textContent = '‹';
    prev.disabled = currentPage === 1;
    prev.onclick = () => loadProducts(currentPage - 1);
    container.appendChild(prev);

    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement('button');
        btn.textContent = i;
        btn.classList.toggle('btn-active', i === currentPage);
        btn.onclick = () => loadProducts(i);
        container.appendChild(btn);
    }

    const next = document.createElement('button');
    next.textContent = '›';
    next.disabled = currentPage >= totalPages;
    next.onclick = () => loadProducts(currentPage + 1);
    container.appendChild(next);
}

function openProductModal() {
    document.getElementById('createProductModal').style.display = 'flex';
    loadEnums('productTypeSelect', '/products/enums/service-types');
    loadEnums('productCategorySelect', '/products/enums/service-categories');
}
function closeProductModal() {
    document.getElementById('createProductModal').style.display = 'none';
}
function loadEnums(selectId, url) {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            const sel = document.getElementById(selectId);
            sel.innerHTML = '';
            data.data.forEach(v => {
                const o = document.createElement('option');
                o.value = v;
                o.textContent = v;
                sel.appendChild(o);
            });
        });
}
function submitProduct() {
    const payload = {
        name: document.getElementById('productName').value,
        productType: document.getElementById('productTypeSelect').value,
        productCategory: document.getElementById('productCategorySelect').value,
        description: document.getElementById('productDescription').value,
        productImage: document.getElementById('productImage').value,
    };
    fetch('/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('등록되었습니다!');
            closeProductModal();
            loadProducts(currentPage);
        } else {
            alert('등록에 실패했습니다.');
        }
    });
}

function editProduct(id) {
    fetch(`/products/${id}`)
        .then(res => res.json())
        .then(data => {
            const p = data.data;
            document.getElementById('editProductName').value = p.name;
            document.getElementById('editProductDescription').value = p.description;
            document.getElementById('editProductImage').value = p.productImage;
            loadEnums('editProductTypeSelect', '/products/enums/service-types');
            loadEnums('editProductCategorySelect', '/products/enums/service-categories');
            editingProductId = id;
            setTimeout(() => {
                document.getElementById('editProductTypeSelect').value = p.productType;
                document.getElementById('editProductCategorySelect').value = p.productCategory;
            }, 100);
            document.getElementById('editProductModal').style.display = 'flex';
        });
}
function closeEditProductModal() {
    document.getElementById('editProductModal').style.display = 'none';
    editingProductId = null;
}
function submitEditProduct() {
    const payload = {
        name: document.getElementById('editProductName').value,
        productType: document.getElementById('editProductTypeSelect').value,
        productCategory: document.getElementById('editProductCategorySelect').value,
        description: document.getElementById('editProductDescription').value,
        productImage: document.getElementById('editProductImage').value,
    };
    fetch(`/products/${editingProductId}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('수정되었습니다!');
            closeEditProductModal();
            loadProducts(currentPage);
        } else {
            alert('수정에 실패했습니다.');
        }
    });
}

function deleteProduct(id) {
    if (!confirm(`ID ${id} 상품을 삭제하시겠습니까?`)) return;
    fetch(`/products/${id}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                loadProducts(currentPage);
            } else {
                alert('삭제에 실패했습니다.');
            }
        });
}

function logout() {
    fetch('/logout', {
        method: 'POST',
        credentials: 'include', // 세션/쿠키 포함
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: ''

    })
        .then(response => {
            window.location.href = '/login.html';
        })
        .catch(error => {
            console.error('로그아웃 실패:', error);
        });
}
