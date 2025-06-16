function fetchPlanStatistics() {
    fetch('/plan-statistics')
        .then(res => res.json())
        .then(json => {
            const labels = json.data.map(item => convertAgeGroup(item.ageGroup));
            const data = json.data.map(item => item.value);

            const ctx = document.getElementById('planChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '연령대별 추천 수',
                        data: data,
                        borderColor: '#e6007e',
                        backgroundColor: '#e6007e',
                        fill: false,
                        tension: 0.3,
                        pointRadius: 5,
                        pointHoverRadius: 7
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 10
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: true
                        }
                    }
                }
            });
        });
}

function convertAgeGroup(code) {
    const map = {
        TEN_S: '10대',
        TWENTY_S: '20대',
        THIRTY_S: '30대',
        FORTY_S: '40대',
        FIFTY_S: '50대',
        SIXTY_S_PLUS: '60대 이상'
    };
    return map[code] || code;
}

function updateCurrentTime() {
    const now = new Date();
    document.getElementById('current-time').textContent = `현재 시간: ${now.toLocaleString()}`;
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

updateCurrentTime();
fetchPlanStatistics();
