const planId = 1; // 실제로는 서버에서 주입하거나 URL에서 추출

let currentPlanPage = 1;
const pageSize = 5;
let lastPlanPage = false;
let currentPlanId = "all"; // 'all' 또는 숫자 planId

// document.addEventListener('DOMContentLoaded', () => {
//     loadPlanOptions(); // 첫 로딩
//
//     const select = document.getElementById('plan-select');
//     select.addEventListener('change', (e) => {
//         const value = e.target.value;
//
//         if (value === 'load-more') {
//             // 다시 이전 선택 유지
//             e.target.selectedIndex = 0;
//             loadPlanOptions(true);
//             return;
//         }
//
//         // ✅ 선택된 요금제로 리뷰 조회
//         currentPlanId = value;
//         loadReviews(0);
//     });
// });
// async function loadPlanOptions(append = false) {
//     if (lastPlanPage || currentPlanPage < 0) return;
//
//     try {
//         const res = await fetch(`/plans?pageNumber=${currentPlanPage}&pageSize=${pageSize}`);
//         const result = await res.json();
//         const responseData = result.data;
//
//         const plans = responseData.planResponseList;
//         const totalElements = responseData.totalElements;
//
//         const select = document.getElementById('plan-select');
//
//         // "더보기..." 제거
//         const moreOption = select.querySelector('option[value="load-more"]');
//         if (moreOption) moreOption.remove();
//
//         plans.forEach(plan => {
//             const option = document.createElement('option');
//             option.value = plan.id;
//             option.textContent = plan.name;
//             select.appendChild(option);
//         });
//
//         // 마지막 페이지 계산 (직접)
//         const totalLoaded = (currentPlanPage + 1) * pageSize;
//         if (totalLoaded >= totalElements) {
//             lastPlanPage = true;
//         } else {
//             const more = document.createElement('option');
//             more.value = 'load-more';
//             more.textContent = '더보기...';
//             select.appendChild(more);
//             currentPlanPage++;
//         }
//
//     } catch (e) {
//         console.error("요금제 불러오기 실패:", e);
//     }
// }

document.addEventListener('DOMContentLoaded', () => {
    const selected = document.getElementById('selected-plan');
    const dropdown = document.getElementById('plan-options');

    selected.addEventListener('click', () => {
        dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
    });

    // 바깥 클릭 시 닫기
    document.addEventListener('click', (e) => {
        if (!e.target.closest('.custom-select')) {
            dropdown.style.display = 'none';
        }
    });

    loadReviews();
    loadPlanOptionsToDropdown(); // 초기 로딩
});


const dropdownPageSize = 20;
let dropdownLastPage = false;
let isAllOptionAdded = false; // "전체 보기" 중복 생성 방지용


let dropdownPlanPage = 1;
let totalLoadedPlans = 0; // 누적 로딩 수

async function loadPlanOptionsToDropdown() {
    if (dropdownLastPage) return;

    try {
        const res = await fetch(`/plans?pageNumber=${dropdownPlanPage}&pageSize=${dropdownPageSize}`);
        const result = await res.json();
        const plans = result.data.planResponseList;
        const total = result.data.totalElements;

        const dropdown = document.getElementById('plan-options');

        // 기존 "더보기..." 제거
        const more = dropdown.querySelector('.dropdown-more');
        if (more) more.remove();

        // "전체 보기"는 한 번만
        if (!isAllOptionAdded) {
            const allItem = document.createElement('div');
            allItem.textContent = '전체 보기';
            allItem.dataset.planId = 'all';
            allItem.addEventListener('click', () => {
                document.getElementById('selected-plan').textContent = '전체 보기';
                currentPlanId = 'all';
                document.getElementById('plan-options').style.display = 'none';
                loadReviews(0);
            });
            dropdown.appendChild(allItem);
            isAllOptionAdded = true;
        }

        // 요금제 항목 추가
        plans.forEach(plan => {
            const item = document.createElement('div');
            item.textContent = plan.name;
            item.dataset.planId = plan.id;
            item.addEventListener('click', () => {
                document.getElementById('selected-plan').textContent = plan.name;
                currentPlanId = plan.id;
                dropdown.style.display = 'none';
                loadReviews(0);
            });
            dropdown.appendChild(item);
        });

        // 누적된 개수 업데이트
        totalLoadedPlans += plans.length;

        if (totalLoadedPlans < total) {
            const moreBtn = document.createElement('div');
            moreBtn.className = 'dropdown-more';
            moreBtn.textContent = '더보기...';
            moreBtn.style.fontWeight = 'bold';
            moreBtn.style.color = '#888';
            moreBtn.addEventListener('click', (e) => {
                e.stopPropagation(); // 닫히는 것 방지
                dropdownPlanPage++;
                loadPlanOptionsToDropdown();
            });
            dropdown.appendChild(moreBtn);
        } else {
            dropdownLastPage = true;
        }

    } catch (error) {
        console.error('요금제 목록 로딩 실패:', error);
    }
}




// 리뷰 조회
async function loadReviews(page = 0) {
    try {
        let url = "";
        if (currentPlanId === "all") {
            url = `/review?page=${page}&size=${pageSize}`;
        } else {
            url = `/review/${currentPlanId}?page=${page}&size=${pageSize}`;
        }

        const res = await fetch(url);
        const data = await res.json();
        const reviews = data.data.content;

        const container = document.getElementById('reviews-container');
        const template = document.getElementById('review-template');


        const selectedPlan = document.getElementById('selected-plan').textContent;
        const avgStar = Number(data.data.avgStar).toFixed(1);
        const totalReview = data.data.totalElements;


        document.getElementById('total-review').textContent = totalReview + ' 개';
        document.getElementById('avg-stars').textContent = avgStar;
        document.getElementById('selected-plan-title').textContent = selectedPlan && selectedPlan !== '전체 보기' && selectedPlan !== '요금제 선택' ? selectedPlan : '';

        container.innerHTML = '';
        reviews.forEach(review => {
            renderReview(review, template, container);
        });

        currentPage = data.data.page;
        totalPages = data.data.totalPages;

        renderPaginationButtons(totalPages, currentPage);
    } catch (error) {
        console.error('리뷰 로딩 실패:', error);
    }
}




// 리뷰 삭제
async function deleteReview(reviewId) {
    try {
        const response = await fetch(`/review/${reviewId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            const result = await response.json().catch(() => ({})); // JSON 파싱 실패 방지
            // 서버에서 내려준 커스텀 예외 메시지를 alert로 출력
            alert(result.message || "리뷰 삭제 실패");
            return;
        }

        alert("리뷰가 삭제되었습니다.");
        loadReviews();

    } catch (error) {
        console.error("리뷰 삭제 오류:", error);
        alert("리뷰 삭제에 실패했습니다.");
    }
}




// 리뷰 item
function renderReview(review, template, container) {
    const clone = template.content.cloneNode(true);

    clone.querySelector('.username').textContent = review.userName;
    clone.querySelector(".plan-name").textContent = review.planName;
    clone.querySelector('.review-date').textContent = new Date(review.createDate).toLocaleDateString();

    const filled = '<span class="star">★</span>'.repeat(review.star);
    const empty = '<span class="star empty">☆</span>'.repeat(5 - review.star);
    clone.querySelector('.stars').innerHTML = filled + empty;
    clone.querySelector('.rating-text').textContent = `${review.star}점`;
    clone.querySelector('.like-count').textContent = review.likeCnt;
    const contentElement = clone.querySelector('.review-content');
    const expandBtn = clone.querySelector('.expand-btn');
    contentElement.textContent = review.content;

    // 100자 이상이면 펼치기 버튼 표시
    if (review.content.length > 100) {
        expandBtn.style.display = 'inline'; // 버튼 보이기
        contentElement.classList.add('collapsed');

        expandBtn.addEventListener('click', () => {
            const isCollapsed = contentElement.classList.toggle('collapsed');
            expandBtn.textContent = isCollapsed ? '펼치기 ⌄' : '접기 ⌃';
        });
    }



    const deleteBtn = clone.querySelector('.delete-btn');
    deleteBtn.addEventListener('click', () => {
        if (confirm("정말 이 리뷰를 삭제하시겠습니까?")) {
            deleteReview(review.reviewId);
        }
    });


    // 좋아요 수
    const likeCountSpan = clone.querySelector('.like-count');
    likeCountSpan.textContent = review.likeCnt;

    container.appendChild(clone);
}



// 페이지네이션
function renderPaginationButtons(totalPages, currentPage) {
    const paginationContainer = document.getElementById('pagination-buttons');
    paginationContainer.innerHTML = '';

    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement('button');
        btn.textContent = i + 1;
        btn.classList.add('page-btn');
        if (i === currentPage) btn.classList.add('active');

        btn.disabled = i === currentPage;
        btn.addEventListener('click', () => loadReviews(i));
        paginationContainer.appendChild(btn);
    }
}