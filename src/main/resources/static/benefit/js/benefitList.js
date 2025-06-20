let editingBenefitId = null;

const pageSize = 20;
let currentPage = 1;
let totalPages = 0;

document.addEventListener('DOMContentLoaded', () => {
    loadBenefits(currentPage);
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


function loadBenefits(pageNumber) {
    if (totalPages > 0) {
        pageNumber = Math.max(1, Math.min(pageNumber, totalPages));
    }
    fetch(`/benefits?pageNumber=${pageNumber}&pageSize=${pageSize}`)
        .then(res => res.json())
        .then(data => {
            const { benefitResponseList, pageNumber: serverPage, totalElements } = data.data;
            currentPage = serverPage;
            totalPages = Math.ceil(totalElements / pageSize);

            renderTable(benefitResponseList);
            renderPagination();
        })
        .catch(err => alert('혜택 데이터를 불러오는 중 오류 발생'));
}

function renderTable(list) {
    const tbody = document.getElementById('benefitTableBody');
    tbody.innerHTML = '';
    list.forEach(b => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${b.id}</td>
            <td>${b.benefitType}</td>
            <td>${b.name}</td>
            <td>${b.description}</td>
            <td>${b.voiceLimit ?? '-'}</td>
            <td>${b.smsLimit ?? '-'}</td>
            <td>${b.discountAmount ?? '-'}</td>
            <td>
                <button class="btn btn-primary" onclick="editBenefit(${b.id})">수정</button>
                <button class="btn btn-ghost" onclick="deleteBenefit(${b.id})">삭제</button>
            </td>`;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById('pagination');
    container.innerHTML = '';

    const prevBtn = document.createElement('button');
    prevBtn.textContent = '‹';
    prevBtn.disabled = currentPage === 1;
    prevBtn.onclick = () => loadBenefits(currentPage - 1);
    container.appendChild(prevBtn);

    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement('button');
        btn.textContent = i;
        btn.classList.toggle('btn-active', i === currentPage);
        btn.onclick = () => loadBenefits(i);
        container.appendChild(btn);
    }

    const nextBtn = document.createElement('button');
    nextBtn.textContent = '›';
    nextBtn.disabled = currentPage >= totalPages;
    nextBtn.onclick = () => loadBenefits(currentPage + 1);
    container.appendChild(nextBtn);
}

function openModal() {
    document.getElementById('createModal').style.display = 'flex';
    loadBenefitTypes('benefitTypeSelect');
}
function closeModal() {
    document.getElementById('createModal').style.display = 'none';
}
function loadBenefitTypes(selectId) {
    fetch('/benefits/enums/benefit-types')
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
function submitBenefit() {
    const payload = {
        benefitType: document.getElementById('benefitTypeSelect').value,
        name: document.getElementById('benefitName').value,
        description: document.getElementById('benefitDescription').value,
        voiceLimit: parseInt(document.getElementById('voiceLimit').value) || null,
        smsLimit: parseInt(document.getElementById('smsLimit').value) || null,
        discountAmount: parseInt(document.getElementById('discountAmount').value) || null,
    };
    fetch('/benefits', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('등록되었습니다!');
            closeModal();
            loadBenefits(currentPage);
        } else {
            alert('등록에 실패했습니다.');
        }
    });
}

function editBenefit(id) {
    fetch(`/benefits/${id}`)
        .then(res => res.json())
        .then(data => {
            const b = data.data;
            document.getElementById('editBenefitName').value = b.name;
            document.getElementById('editBenefitDescription').value = b.description;
            document.getElementById('editVoiceLimit').value = b.voiceLimit ?? '';
            document.getElementById('editSmsLimit').value = b.smsLimit ?? '';
            document.getElementById('editDiscountAmount').value = b.discountAmount ?? '';
            loadBenefitTypes('editBenefitTypeSelect');
            editingBenefitId = id;
            setTimeout(() => {
                document.getElementById('editBenefitTypeSelect').value = b.benefitType;
            }, 100);
            document.getElementById('editModal').style.display = 'flex';
        });
}
function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
    editingBenefitId = null;
}
function submitEditBenefit() {
    const payload = {
        benefitType: document.getElementById('editBenefitTypeSelect').value,
        name: document.getElementById('editBenefitName').value,
        description: document.getElementById('editBenefitDescription').value,
        voiceLimit: parseInt(document.getElementById('editVoiceLimit').value) || null,
        smsLimit: parseInt(document.getElementById('editSmsLimit').value) || null,
        discountAmount: parseInt(document.getElementById('editDiscountAmount').value) || null,
    };
    fetch(`/benefits/${editingBenefitId}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).then(res => {
        if (res.ok) {
            alert('수정되었습니다!');
            closeEditModal();
            loadBenefits(currentPage);
        } else {
            alert('수정에 실패했습니다.');
        }
    });
}

function deleteBenefit(id) {
    if (!confirm(`ID ${id} 혜택을 삭제하시겠습니까?`)) return;
    fetch(`/benefits/${id}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                loadBenefits(currentPage);
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

