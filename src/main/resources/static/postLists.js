document.addEventListener('DOMContentLoaded', function() {
    const slider = document.getElementById('slider');
    const pagination = document.getElementById('pagination');
    const postsPerPage = 3; // Số bài viết trên mỗi trang
    let currentPage = 1;

    // Dữ liệu giả lập
    const data = {
        posts: [
            { id: 1, title: 'Post Title 1', author: 'Author 1', thumbnail: '/img/Layer 1.png' },
            { id: 2, title: 'Post Title 2', author: 'Author 2', thumbnail: '/img/Layer 1.png' },
            { id: 3, title: 'Post Title 3', author: 'Author 3', thumbnail: '/img/Layer 1.png' },
            { id: 4, title: 'Post Title 4', author: 'Author 4', thumbnail: '/img/Layer 1.png' },
            { id: 5, title: 'Post Title 5', author: 'Author 5', thumbnail: '/img/Layer 1.png' },
            { id: 6, title: 'Post Title 6', author: 'Author 6', thumbnail: '/img/Layer 1.png' }
        ]
    };

    function renderPosts(page) {
        slider.innerHTML = '';
        const start = (page - 1) * postsPerPage;
        const end = start + postsPerPage;
        const postsToShow = data.posts.slice(start, end);

        postsToShow.forEach(post => {
            // Tạo các phần tử HTML
            const slide = document.createElement('div');
            slide.classList.add('slide');
            slide.setAttribute('data-id', post.id);

            const postCard = document.createElement('div');
            postCard.classList.add('post-card');


            const img = document.createElement('img');
            img.src = post.thumbnail;
            img.alt = `Thumbnail for ${post.title}`;

            const title = document.createElement('h3');
            title.textContent = post.title;

            const author = document.createElement('p');
            author.textContent = `Author: ${post.author}`;

            const text = document.createElement('div');
            const postContent = document.createElement('div');
            postContent.classList.add('postContent');
            // Tạo nhóm nút CRUD
            const btnGroup = document.createElement('div');
            btnGroup.classList.add('btn-group');

            const btnEdit = document.createElement('button');
            btnEdit.textContent = 'Edit';
            btnEdit.addEventListener('click', () => handleEdit(post.id));

            const btnDelete = document.createElement('button');
            btnDelete.textContent = 'Delete';
            btnDelete.classList.add('btn_delete');
            btnDelete.addEventListener('click', () => handleDelete(post.id));

            const btnView = document.createElement('button');
            btnView.textContent = 'View';
            btnView.addEventListener('click', () => handleView(post.id));


            text.appendChild(title);
            text.appendChild(author);

            // Chèn các nút vào nhóm nút
            btnGroup.appendChild(btnEdit);
            btnGroup.appendChild(btnView);
            btnGroup.appendChild(btnDelete);

            postContent.appendChild(img)
            postContent.appendChild(text);

            // Chèn các phần tử vào DOM
            postCard.appendChild(postContent);
            postCard.appendChild(btnGroup);
            slide.appendChild(postCard);
            slider.appendChild(slide);
        });
    }

    function setupPagination() {
        pagination.innerHTML = '';
        const pageCount = Math.ceil(data.posts.length / postsPerPage);

        for (let i = 1; i <= pageCount; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.addEventListener('click', () => {
                currentPage = i;
                renderPosts(currentPage);
            });

            if (i === currentPage) {
                btn.style.fontWeight = 'bold';
            }

            pagination.appendChild(btn);
        }
    }

    function handleEdit(id) {
        console.log('Edit post with ID:', id);
        // Thêm mã để xử lý chỉnh sửa bài viết
    }

    function handleDelete(id) {
        console.log('Delete post with ID:', id);
        // Thêm mã để xử lý xóa bài viết
    }

    function handleView(id) {
        console.log('View post with ID:', id);
        // Thêm mã để xử lý xem chi tiết bài viết
    }

    // Khởi tạo trang và phân trang
    renderPosts(currentPage);
    setupPagination();
});
