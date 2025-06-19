// chatStatistics.js

// 전역 변수
let chartsData = {};
let currentTab = 'users';
let currentPeriod = 'daily';

const labelMap = {
    users: {
        daily: '일간 활성 사용자 수',
        weekly: '주간 활성 사용자 수',
        monthly: '월간 활성 사용자 수'
    },
    messages: {
        daily: '일간 메시지 수',
        weekly: '주간 메시지 수',
        monthly: '월간 메시지 수'
    }
};

let chartInstance = null;

// 차트 그리기 함수
function renderChart(dataKey, label) {
    const stats = chartsData[dataKey] || [];
    const ctx = document.getElementById('mainChart').getContext('2d');
    if (chartInstance) chartInstance.destroy();
    chartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: stats.map(x => x.statDate),
            datasets: [{
                label,
                data: stats.map(x => x.value),
                borderColor: '#e6007e',
                backgroundColor: '#e6007e',
                fill: false,
                tension: 0.2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true } },
            plugins: {
                legend: { display: false },
                title: { display: true, text: label, font: { size: 18 } }
            }
        }
    });
}

// 데이터 fetch 및 렌더링
async function fetchAndDraw() {
    try {
        const res = await fetch('/chat-statistics');
        if (!res.ok) {
            alert(`HTTP 오류: ${res.status}`);
            return;
        }
        const json = await res.json();
        const data = json.data;

        const summaryEl = document.getElementById('content-summary');
        if (summaryEl) {
            summaryEl.innerHTML = json.data.content;
        }
        chartsData = {
            'users-daily': data.dailyActiveUsers,
            'users-weekly': data.weeklyActiveUsers,
            'users-monthly': data.monthlyActiveUsers,
            'messages-daily': data.dailyMessageCounts,
            'messages-weekly': data.weeklyMessageCounts,
            'messages-monthly': data.monthlyMessageCounts
        };

        // 초기 차트 그리기
        renderChart(`${currentTab}-${currentPeriod}`, labelMap[currentTab][currentPeriod]);
    } catch (err) {
        console.error(err);
        alert(`데이터 불러오기 오류: ${err.message}`);
    }
}

// 탭 이벤트 바인딩
function initTabs() {
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentTab = btn.dataset.type;  // dataset.type 사용
            renderChart(`${currentTab}-${currentPeriod}`, labelMap[currentTab][currentPeriod]);
        });
    });
    document.querySelectorAll('.sub-tab-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.sub-tab-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentPeriod = btn.dataset.period;
            renderChart(`${currentTab}-${currentPeriod}`, labelMap[currentTab][currentPeriod]);
        });
    });
}

// 현재 시간 표시
function updateCurrentTime() {
    const now = new Date();
    const formatted = now.toLocaleString('ko-KR', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit', second: '2-digit'
    });
    document.getElementById('current-time').textContent = `현재 시각: ${formatted}`;
}

// 로그아웃 처리
function logout() {
    fetch('/logout', { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, body: '' })
        .then(() => window.location.href = '/login.html')
        .catch(err => console.error('로그아웃 실패:', err));
}

// 초기화
window.addEventListener('DOMContentLoaded', () => {
    updateCurrentTime();
    setInterval(updateCurrentTime, 1000);
    initTabs();
    fetchAndDraw();
});
