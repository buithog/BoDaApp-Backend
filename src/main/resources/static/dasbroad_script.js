// let 



document.addEventListener("DOMContentLoaded", function() {
    const slides = document.querySelectorAll('.slide');
    let currentSlide = 1; // Hiển thị slide ở giữa đầu tiên
    let slideInterval;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.remove('active');
            if (i === index) {
                slide.classList.add('active');
            }
        });
    }

    function nextSlide() {
        currentSlide = (currentSlide + 1) % slides.length;
        showSlide(currentSlide);
    }

    function startSlideShow() {
        slideInterval = setInterval(nextSlide, 2000); // Chuyển slide mỗi 3 giây
    }

    function stopSlideShow() {
        clearInterval(slideInterval);
    }

    slides.forEach((slide, index) => {
        slide.addEventListener('mouseenter', () => {
            stopSlideShow();
            showSlide(index);
        });
        slide.addEventListener('mouseleave', startSlideShow);
    });

    showSlide(currentSlide);
    startSlideShow();
});
