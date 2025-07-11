let editingProductId = null;

const pageSize = 20;
let currentPage = 1;
let totalPages = 0;
let imageUrl = '';

document.addEventListener('DOMContentLoaded', () => {
    loadProducts(currentPage);
});

document.getElementById('run-plan-batch')
    .addEventListener('click', async () => {
            if (!confirm('요금제 배치 작업을 실행하시겠습니까?')) return;
            try {
                const resp = await fetch('/batch/plan/run', {
                    method: 'GET',
                    credentials: 'include'
                });
                alert('요금제 배치 작업 완료');

            } catch (e) {
                console.error(e);
                alert('요금제 배치 호출 중 오류 발생');
            }

        }
    );

document.getElementById('run-chat-batch')
    .addEventListener('click', async () => {
            if (!confirm('채팅 배치 작업을 실행하시겠습니까?')) return;
            try {
                const resp = await fetch('/batch/chat-bot/run', {
                    method: 'GET',
                    credentials: 'include'
                });
                alert('채팅 배치 작업 완료');

            } catch (e) {
                console.error(e);
                alert('채팅 배치 호출 중 오류 발생');
            }

        }
    );

function loadProducts(pageNumber) {
    if (totalPages > 0) {
        pageNumber = Math.max(1, Math.min(pageNumber, totalPages));
    }
    fetch(`/products?pageNumber=${pageNumber}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(data => {
            const {productResponseList, pageNumber: serverPage, totalElements} = data.data;
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
        let imgText;
        if (p.productImage) {
            if (typeof p.productImage === 'object') {
                imgText = p.productImage.data ?? '';
            } else {
                try {
                    const obj = JSON.parse(p.productImage);
                    imgText = obj.data ?? '';
                } catch {
                    imgText = p.productImage;
                }
            }
        } else {
            imgText = '';
        }

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.productType}</td>
            <td>${p.productCategory}</td>
            <td>${p.description}</td>
            <td>${imgText}</td>
            <td>
                <button class="btn btn-delete" onclick="deleteProductImg(${p.id}, '${imgText}')">이미지 삭제</button>
                <button class="btn btn-primary" onclick="editProduct(${p.id})">수정</button>
                <button class="btn btn-ghost" onclick="deleteProduct(${p.id}, '${imgText}')">삭제</button>
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

function uploadProductImage(fileInputId, formKey, oldUrl, callback) {
    const fileInput = document.getElementById(fileInputId);
    const file = fileInput.files[0];

    const formData = new FormData();
    formData.append(formKey, file); // file -> @RequestPart
    if (oldUrl) {
        formData.append("oldUrl", oldUrl); // oldUrl -> @RequestParam
    }

    fetch("/products/image", {
        method: "POST",
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 응답 오류: ${response.status}`);
            }
            return response.json(); // JSON 응답으로 파싱
        })
        .then(json => {
            const imageUrl = json.data; // data 필드만 추출
            console.log("업로드 성공:", imageUrl);
            if (callback) callback(imageUrl);
        })
        .catch(error => {
            console.error("업로드 실패:", error);
        });
}

function submitProduct() {
    uploadProductImage('file', 'file', null, function(url) {
        const payload = {
            name: document.getElementById('productName').value,
            productType: document.getElementById('productTypeSelect').value,
            productCategory: document.getElementById('productCategorySelect').value,
            description: document.getElementById('productDescription').value,
            productImage: url
        };

        fetch('/products', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
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
    });

    const fileInput = document.getElementById('file');
    fileInput.value = null;
}

function editProduct(id) {
    fetch(`/products/${id}`)
        .then(res => res.json())
        .then(data => {
            const p = data.data;

            // 1) 기존 p.productImage 에서 URL만 꺼내기
            let raw = p.productImage;
            let imageUrl = '';

            if (raw) {
                if (typeof raw === 'string') {
                    // 문자열(JSON) 형태면 파싱
                    try {
                        const obj = JSON.parse(raw);
                        imageUrl = obj.data || '';
                    } catch {
                        imageUrl = raw;
                    }
                } else if (raw.data) {
                    // 이미 객체 형태면 바로
                    imageUrl = raw.data;
                }
            }

            // 2) input 값과 img 태그에 URL 세팅
            document.getElementById('editProductImageUrl').value = imageUrl;
            const imgEl = document.getElementById('editProductImage');
            imgEl.src = imageUrl;

            // (선택) 이미지가 잘려 보이지 않도록 스타일 보정
            imgEl.style.maxWidth = '100%';
            imgEl.style.height   = 'auto';

            // 나머지 필드 세팅
            document.getElementById('editProductName').value = p.name;
            document.getElementById('editProductDescription').value = p.description;
            loadEnums('editProductTypeSelect', '/products/enums/service-types');
            loadEnums('editProductCategorySelect', '/products/enums/service-categories');
            editingProductId = id;
            setTimeout(() => {
                document.getElementById('editProductTypeSelect').value = p.productType;
                document.getElementById('editProductCategorySelect').value = p.productCategory;
            }, 100);
            document.getElementById('editProductModal').style.display = 'flex';
        });

    const editFileInput = document.getElementById('editFile');
    editFileInput.value = null;
}


function closeEditProductModal() {
    document.getElementById('editProductModal').style.display = 'none';
    editingProductId = null;
}

function updateProduct(imageUrl) {
    const payload = {
        name: document.getElementById('editProductName').value,
        productType: document.getElementById('editProductTypeSelect').value,
        productCategory: document.getElementById('editProductCategorySelect').value,
        description: document.getElementById('editProductDescription').value,
        productImage: imageUrl
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

function submitEditProduct() {
    const oldUrl = document.getElementById('editProductImageUrl').value;
    const fileInput = document.getElementById('editFile');

    // 새 이미지 없으면 기존 URL 그대로 수정
    if (!fileInput.files || fileInput.files.length === 0) {
        updateProduct(oldUrl);
        return;
    }

    // 새 이미지 있으면 oldUrl도 함께 전달하여 삭제 후 업로드
    uploadProductImage('editFile', 'file', oldUrl, function(url) {
        updateProduct(url);
    });
}

function deleteProductImg(id, imageUrl) {
    if (!imageUrl) {
        alert("이미지가 없습니다.");
        return;
    }

    if (!confirm("정말 이 이미지를 삭제하시겠습니까?")) return;

    fetch(`/products/${id}/image?imageAddr=${encodeURIComponent(imageUrl)}`, {
        method: 'DELETE'
    })
        .then(res => {
            if (res.ok) {
                alert("이미지 삭제 완료");
                loadProducts(currentPage); // 테이블 새로고침
            } else {
                alert("이미지 삭제 실패");
            }
        })
        .catch(err => {
            console.error("이미지 삭제 중 오류:", err);
            alert("이미지 삭제 중 오류 발생");
        });
}

function deleteProduct(id, imageUrl) {
    if (!confirm(`ID ${id} 상품을 삭제하시겠습니까?`)) return;

    let url = `/products/${id}`;
    if (imageUrl) {
        url += `?imageUrl=${encodeURIComponent(imageUrl)}`;
    }

    fetch(url, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                alert('삭제되었습니다.');
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
