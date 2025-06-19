document.addEventListener('DOMContentLoaded', () => {
    updateCurrentTime();
    setInterval(updateCurrentTime, 1000); // 1초마다 갱신

    fetchPlanStatistics();
});

function fetchPlanStatistics() {
    fetch('/plan-statistics')
        .then(res => res.json())
        .then(json => {
            document.getElementById('summary-loading')?.classList.add('hidden');
            const contentEl = document.getElementById('statistic-content');
            if (contentEl) {
                contentEl.textContent = json.data.content;
                contentEl.classList.remove('hidden');
            }
            const toggleBtn = document.querySelector('#summary-card .close-btn');
            if (toggleBtn) toggleBtn.textContent = '▲';

            document.getElementById('chart-loading')?.classList.add('hidden');
            document.getElementById('planChart')?.classList.remove('hidden');

            const { planStatisticResponses } = json.data;
            const labels = planStatisticResponses.map(item => convertAgeGroup(item.ageGroup));
            const data = planStatisticResponses.map(item => item.value);
            const ctx = document.getElementById('planChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: { labels, datasets: [{
                        label: '연령대별 추천 수',
                        data,
                        borderColor: '#e6007e',
                        backgroundColor: '#e6007e',
                        fill: false,
                        tension: 0.3,
                        pointRadius: 5,
                        pointHoverRadius: 7
                    }]},
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: { y: { beginAtZero: true, ticks: { stepSize: 10 } } },
                    plugins: { legend: { display: true } }
                }
            });
        });
}

function toggleSummaryContent() {
    const contentEl = document.getElementById('statistic-content');
    const btn = document.querySelector('#summary-card .close-btn');
    if (!contentEl || !btn) return;

    const isHidden = contentEl.classList.toggle('hidden');
    btn.textContent = isHidden ? '▼' : '▲';
}

function convertAgeGroup(code) {
    const map = { TEN_S: '10대', TWENTY_S: '20대', THIRTY_S: '30대', FORTY_S: '40대', FIFTY_S: '50대', SIXTY_S_PLUS: '60대 이상' };
    return map[code] || code;
}

function updateCurrentTime() {
    const now = new Date();
    document.getElementById('current-time').textContent = `현재 시간: ${now.toLocaleString()}`;
}

function logout() {
    fetch('/logout', { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, body: '' })
        .then(() => window.location.href = '/login.html')
        .catch(err => console.error('로그아웃 실패:', err));
}
