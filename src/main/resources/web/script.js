const input = document.getElementById('searchInput');
const resultsDiv = document.getElementById('results');
const loading = document.getElementById('loading');

input.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        search();
    }
});

async function search() {
    const query = input.value.trim();
    if (!query) return;

    resultsDiv.innerHTML = '';
    loading.classList.remove('hidden');

    try {
        const response = await fetch(`/api/search?q=${encodeURIComponent(query)}`);
        const data = await response.json();
        
        displayResults(data);
    } catch (error) {
        console.error('Error:', error);
        resultsDiv.innerHTML = '<p class="error">Something went wrong. ensure the server is running.</p>';
    } finally {
        loading.classList.add('hidden');
    }
}

function displayResults(data) {
    if (data.length === 0) {
        resultsDiv.innerHTML = '<p class="no-results">No records found matching your query.</p>';
        return;
    }

    data.forEach((item, index) => {
        const card = document.createElement('div');
        card.className = 'card';
        card.style.animation = `fadeIn 0.5s ease-out forwards ${index * 0.1}s`;
        card.style.opacity = '0'; // Start hidden for anim

        card.innerHTML = `
            <div class="card-title">
                ${item.name}
                <span class="card-type">${item.type}</span>
            </div>
            <div class="stats">
                <div class="stat-item">
                    <span class="stat-val">${item.wins}</span>
                    <span class="stat-label">Wins</span>
                </div>
                <div class="stat-item">
                    <span class="stat-val">${item.podiums}</span>
                    <span class="stat-label">Podiums</span>
                </div>
            </div>
        `;
        resultsDiv.appendChild(card);
    });
}
