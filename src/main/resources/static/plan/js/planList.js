let currentPage = 1;
const pageSize = 20;

document.addEventListener('DOMContentLoaded', () => {
    loadPlans(currentPage);
    loadMultiSelect('benefitSelect', '/benefits/all');
    loadMultiSelect('productSelect', '/products/all');
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
            if (!confirm('요금제 배치 작업을 실행하시겠습니까?')) return;
            try {
                const resp = await fetch('/batch/chat-bot/run', {
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

function loadPlans(page) {
    fetch(`/plans?pageNumber=${page}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('planTableBody');
            tbody.innerHTML = '';
            data.data.planResponseList.forEach(plan => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${plan.id}</td>
                    <td>${plan.name}</td>
                    <td>${plan.networkType}</td>
                    <td>${plan.planCategory}</td>
                    <td>${plan.basePrice}</td>
                    <td>${plan.dataAllowance}</td>
                    <td>${plan.tetheringSharingAllowance}</td>
                    <td>${plan.speedAfterLimit}</td>
                    <td>${plan.description || '-'}</td>
                    <td>${(plan.benefitResponseList||[]).map(b => b.name).join(', ') || '-'}</td>
                    <td>${(plan.productResponseList||[]).map(p => p.name).join(', ') || '-'}</td>
                    <td>
                        <button class="btn btn-primary edit-btn">수정</button>
                        <button class="btn btn-ghost" onclick="deletePlan(${plan.id})">삭제</button>
                    </td>`;
                tbody.appendChild(row);
                row.querySelector('.edit-btn')
                    .addEventListener('click', () => openEditModal(plan));
            });
            renderPagination(data.data.pageNumber + 1, data.data.pageSize, data.data.totalElements);
        });
}

function renderPagination(current, size, total) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';
    const totalPages = Math.ceil(total / size);
    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement('button');
        btn.textContent = i;
        btn.className = i === current ? 'btn btn-active' : 'btn';
        btn.onclick = () => { currentPage = i; loadPlans(i); };
        pagination.appendChild(btn);
    }
}

function deletePlan(planId) {
    if (!confirm('정말 삭제하시겠습니까?')) return;
    fetch(`/plans/${planId}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                alert('삭제되었습니다.');
                loadPlans(currentPage);
            } else {
                alert('삭제에 실패했습니다.');
            }
        });
}

function openPlanModal() {
    document.getElementById('createPlanModal').classList.add('show');
}

function closePlanModal() {
    document.getElementById('createPlanModal').classList.remove('show');
}

function submitPlan() {
    const payload = {
        name: document.getElementById('planName').value,
        networkType: document.getElementById('networkTypeSelect').value,
        planCategory: document.getElementById('planCategorySelect').value,
        basePrice: +document.getElementById('planBasePrice').value,
        dataAllowance: +document.getElementById('planData').value,
        tetheringSharingAllowance: +document.getElementById('planTethering').value,
        speedAfterLimit: +document.getElementById('planSpeedLimit').value,
        description: document.getElementById('planDescription').value,
        benefitIdList: Array.from(
            document.getElementById('benefitSelect').selectedOptions
        ).map(o => +o.value),
        productIdList: Array.from(
            document.getElementById('productSelect').selectedOptions
        ).map(o => +o.value)
    };
    fetch('/plans', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('요금제가 등록되었습니다.');
            closePlanModal();
            loadPlans(currentPage);
        } else {
            alert('등록에 실패했습니다.');
        }
    });
}

function loadMultiSelect(selectId, url) {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            const sel = document.getElementById(selectId);
            sel.innerHTML = '';
            const list = url.includes('benefits')
                ? data.data.benefitResponseList
                : data.data.productResponseList;
            list.forEach(item => {
                const o = document.createElement('option');
                o.value = item.id;
                o.textContent = `${item.name} (${item.id})`;
                sel.appendChild(o);
            });
        });
}

function openEditModal(plan) {
    // 기본 필드 채우기
    document.getElementById('editPlanId').value = plan.id;
    document.getElementById('editPlanName').value = plan.name;
    document.getElementById('editNetworkTypeSelect').value = plan.networkType;
    document.getElementById('editPlanCategorySelect').value = plan.planCategory;
    document.getElementById('editPlanBasePrice').value = plan.basePrice;
    document.getElementById('editPlanData').value = plan.dataAllowance;
    document.getElementById('editPlanTethering').value = plan.tetheringSharingAllowance;
    document.getElementById('editPlanSpeedLimit').value = plan.speedAfterLimit;
    document.getElementById('editPlanDescription').value = plan.description || '';

    // 혜택 목록 불러오기 (/benefits/all)
    fetch('/benefits/all')
        .then(r => r.json())
        .then(d => {
            const sel = document.getElementById('editBenefitSelect');
            sel.innerHTML = '';
            d.data.benefitResponseList.forEach(b => {
                const o = document.createElement('option');
                o.value = b.id;
                o.textContent = `${b.name} (${b.id})`;
                if (plan.benefitResponseList?.some(pb => pb.id === b.id)) {
                    o.selected = true;
                }
                sel.appendChild(o);
            });
        });

    // 상품 목록 불러오기 (/products/all)
    fetch('/products/all')
        .then(r => r.json())
        .then(d => {
            const sel = document.getElementById('editProductSelect');
            sel.innerHTML = '';
            d.data.productResponseList.forEach(p => {
                const o = document.createElement('option');
                o.value = p.id;
                o.textContent = `${p.name} (${p.id})`;
                if (plan.productResponseList?.some(pp => pp.id === p.id)) {
                    o.selected = true;
                }
                sel.appendChild(o);
            });
        });

    document.getElementById('editPlanModal').classList.add('show');
}

function closeEditModal() {
    document.getElementById('editPlanModal').classList.remove('show');
}

function submitEditPlan() {
    const id = document.getElementById('editPlanId').value;
    const payload = {
        name: document.getElementById('editPlanName').value,
        networkType: document.getElementById('editNetworkTypeSelect').value,
        planCategory: document.getElementById('editPlanCategorySelect').value,
        basePrice: +document.getElementById('editPlanBasePrice').value,
        dataAllowance: +document.getElementById('editPlanData').value,
        tetheringSharingAllowance: +document.getElementById('editPlanTethering').value,
        speedAfterLimit: +document.getElementById('editPlanSpeedLimit').value,
        description: document.getElementById('editPlanDescription').value,
        benefitIds: Array.from(
            document.getElementById('editBenefitSelect').selectedOptions
        ).map(o => +o.value),
        serviceIds: Array.from(
            document.getElementById('editProductSelect').selectedOptions
        ).map(o => +o.value)
    };
    fetch(`/plans/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('수정되었습니다.');
            closeEditModal();
            loadPlans(currentPage);
        } else {
            alert('수정에 실패했습니다.');
        }
    });
}
function logout() {
    fetch('/api/auth/logout', {
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
