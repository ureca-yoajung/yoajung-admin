let editingProductId = null;

document.addEventListener('DOMContentLoaded', () => {
    fetch('/products')
        .then(res => res.json())
        .then(data => {
            const list = data.data.productResponseList;
            const tbody = document.getElementById('productTableBody');
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
                        <button class="btn btn-ghost" onclick="deleteProduct(${p.id}, this)">삭제</button>
                    </td>`;
                tbody.appendChild(row);
            });
        });
});

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
            const select = document.getElementById(selectId);
            select.innerHTML = '';
            data.data.forEach(type => {
                const option = document.createElement('option');
                option.value = type;
                option.textContent = type;
                select.appendChild(option);
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
            location.reload();
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
            location.reload();
        } else {
            alert('수정에 실패했습니다.');
        }
    });
}

function deleteProduct(id, btn) {
    if (!confirm(`ID ${id} 상품을 삭제하시겠습니까?`)) return;
    fetch(`/products/${id}`, {
        method: 'DELETE'
    }).then(res => {
        if (res.ok) {
            const row = btn.closest('tr');
            row.remove();
        } else {
            alert('삭제에 실패했습니다.');
        }
    });
}
