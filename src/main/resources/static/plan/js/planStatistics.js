document.addEventListener('DOMContentLoaded', () => {
    updateCurrentTime();
    setInterval(updateCurrentTime, 1000); // 1초마다 갱신

    fetchPlanStatistics();
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

function fetchPlanStatistics() {
    fetch('/plan-statistics')
        .then(res => res.json())
        .then(json => {
            const overlay = document.getElementById('loading-overlay');
            const mainEl = document.getElementById('main-content');

            if (overlay) overlay.style.display = 'flex';
            if (mainEl) mainEl.classList.add('hidden');

            document.getElementById('summary-loading')?.classList.add('hidden');
            const contentEl = document.getElementById('statistic-content');
            if (contentEl) {
                const html = json.data.content
                    .split('\n')
                    .map(line => line.trim())
                    .filter(line => line)
                    .map(line => `<p>${line}</p>`)
                    .join('');
                contentEl.innerHTML = html;
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

            if (overlay) overlay.style.display = 'none';
            if (mainEl) mainEl.classList.remove('hidden');
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
