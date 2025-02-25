<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register Form</title>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <!--        <link rel="stylesheet" href="./CSS/Style.css">-->
        <link rel="stylesheet" href="./CSS/HeaderAndFooter_CSS.css">
        <link rel="stylesheet" href="./CSS/home.css">
        <script src="./Script/header-script.js"></script>
        <style>
            .cart-section {
                border: 1px solid #FC6E51; /* Đường viền màu chủ đạo */
                border-radius: 8px; /* Bo góc phần khung */
                padding: 20px; /* Khoảng cách trong */
                background-color: #fff; /* Màu nền trắng */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
            }

            .cart-title {
                font-size: 20px;
                font-weight: bold;
                color: #FC6E51; /* Màu chữ đồng bộ */
                margin-bottom: 15px;
            }

            .list-group-item {
                border: none; /* Loại bỏ đường viền mặc định */
                padding: 10px 15px; /* Khoảng đệm */
                display: flex;
                justify-content: space-between;
                background-color: #f8f9fa; /* Màu nền nhẹ */
                margin-bottom: 5px; /* Khoảng cách giữa các mục */
                border-radius: 6px; /* Bo góc */
                transition: background-color 0.3s ease; /* Hiệu ứng hover */
            }

            .list-group-item:hover {
                background-color: #ffe8e0; /* Màu nhấn khi hover */
            }

            .minicart-quantity {
                background-color: #FC6E51 !important; /* Giữ màu chủ đạo */
                color: #fff !important; /* Chữ trắng */
                font-size: 14px; /* Cỡ chữ */
            }

            .cart-total {
                font-size: 18px;
                color: #333; /* Màu chữ đậm */
                margin-top: 10px; /* Khoảng cách trên */
                padding-top: 10px;
                border-top: 1px solid #ddd; /* Đường viền phân cách */
            }

            .minicart-btn {
                background-color: #FC6E51;
                color: #fff;
                border: none;
                border-radius: 50px;
                width: 100%; /* Kích thước đầy đủ */
                padding: 10px;
                transition: background-color 0.3s ease, transform 0.2s ease; /* Hiệu ứng */
            }

            .minicart-btn:hover {
                background-color: #e65c41;
                transform: translateY(-2px); /* Nhấn mạnh */
            }
            .pagination a.active {
                background-color: #FC6E51; /* Màu cam */
                color: white; /* Chữ trắng */
                border-color: #FC6E51; /* Màu viền cam */
            }
            
        </style>
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="header.jsp"></jsp:include>

            <!-- Thanh tìm kiếm và menu trong cùng một form -->
            <form action="home-search" method="GET">
                <div class="search-bar">
                    <div class="container">
                        <div class="d-flex justify-content-center">
                            <input type="text" name="search" class="form-control search-input" placeholder="Tìm món...">
                            <button type="submit" class="btn search-btn"><i class="fa-solid fa-search"></i></button>
                        </div>
                    </div>
                </div>
            </form>

            <!-- Product Section + Cart -->
            <div class="container my-5">
                <!-- Menu -->
                <div class="menu-nav">
                    <a href="home" class="col-md">Tất cả</a>
                <c:forEach items="${requestScope.categorys}" var="c">
                    <a href="home?categoryid=${c.id}&index=${i}" class="col-md ${param.categoryid == c.id ? 'active' : ''}">${c.name}</a>
                </c:forEach>

            </div>
        </div>

        <div class="container-fluid main-content px-5">
            <div class="row">
                <!-- Phần giỏ hàng -->
                <div class="col-md-3">
                    <div class="cart-section">
                        <div class="cart-title">Giỏ hàng</div>
                        <c:if test="${not empty cartlists}">
                            <ul class="list-group">
                                <c:forEach items="${cartlists}" var="item">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        ${item.product.name} 
                                        <span class="minicart-quantity badge bg-primary rounded-pill">${item.quantity}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                            <!-- Khai báo biến tổng giá -->
                            <c:set var="totalPrice" value="0" />

                            <!-- Duyệt qua từng item và tính tổng giá -->
                            <c:forEach items="${cartlists}" var="item">
                                <c:set var="itemTotal" value="${item.quantity * item.product.price}" />
                                <c:set var="totalPrice" value="${totalPrice + itemTotal}" />
                            </c:forEach>

                            <!-- Hiển thị tổng giá -->
                            <div class="cart-total mt-3">
                                <strong>Tổng đơn:</strong> 
                                <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true" />
                                <span>VND</span>
                            </div>



                            <a href="cart" class="minicart-btn btn btn-secondary mt-3">Chỉnh sửa giỏ hàng</a>
                        </c:if>
                        <c:if test="${empty cartlists}">
                            <p>Giỏ hàng trống.</p>
                        </c:if>
                    </div>
                </div>

                <!-- Phần sản phẩm -->
                <div class="col-md-9">
                    <div class="row g-4">
                        <c:forEach items="${requestScope.products}" var="p">
                            <c:set var="quantity" value="1" /> <!-- Số lượng mặc định -->
                            <c:forEach items="${cartlists}" var="item">
                                <c:if test="${item.product.id == p.id}">
                                    <c:set var="quantity" value="${item.quantity + 1}" /> <!-- Nếu đã có trong giỏ hàng, cộng thêm 1 -->
                                </c:if>
                            </c:forEach>

                            <div class="col-sm-12 col-md-6 col-lg-3">
                                <div class="card product-card">
                                    <a href="product-detail?pro_id=${p.id}">
                                        <img src="${p.imageUrl != null ? p.imageUrl : 'default-image.jpg'}" class="card-img-top" alt="${p.name}">
                                    </a>
                                    <div class="card-body">
                                        <a style="text-decoration: none" href="product-detail?pro_id=${p.id}">
                                            <h5 class="card-title">${p.name}</h5>
                                        </a>
                                        <p class="card-text">${p.price}</p>
                                        <!-- Thay đổi nút Đặt Món thành một form -->
                                        <form action="updateCart" method="post" style="display:inline;">
                                            <input type="hidden" name="productId" value="${p.id}">
                                            <input type="hidden" name="quantity" value="${quantity}"> <!-- Sử dụng số lượng đã tính -->
                                            <c:if test="${p.quantity > 0}">
                                                <button type="submit" class="btn btn-primary">Đặt Món</button>
                                            </c:if>
                                            <c:if test="${p.quantity <= 0}">
                                                <buttonlass="btn btn-primary">Hết Hàng</button>
                                                
                                            </c:if>

                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>


                    <div class="float-end">
                        <jsp:include page="pagination.jsp">
                            <jsp:param name="baseUrl" value="otherServlet" />
                        </jsp:include>
                    </div>
                </div>

            </div>
        </div>
        <!-- Footer -->
        <jsp:include page="footer.jsp"></jsp:include>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="./Script/Script.js"></script>
        <script>

            document.addEventListener('DOMContentLoaded', function () {
                // Get all menu items
                const menuItems = document.querySelectorAll('.menu-nav a');

                // Get the current URL
                const currentURL = window.location.href;

                // Add click event listener to each menu item
                menuItems.forEach(item => {
                    // Check if this is the current page
                    if (currentURL === item.href) {
                        item.classList.add('active');
                    }

                    // Add click event listener
                    item.addEventListener('click', function () {
                        // Remove active class from all items
                        menuItems.forEach(menuItem => {
                            menuItem.classList.remove('active');
                        });

                        // Add active class to clicked item
                        this.classList.add('active');
                    });
                });

                // If no active item is found, activate "Tất cả" by default
                if (!document.querySelector('.menu-nav a.active')) {
                    const allProductsLink = document.querySelector('.menu-nav a[href="home"]');
                    if (allProductsLink) {
                        allProductsLink.classList.add('active');
                    }
                }
            });
            document.addEventListener('DOMContentLoaded', function () {
                // Get the current URL
                const currentURL = window.location.href;

                // Get all pagination links
                const paginationLinks = document.querySelectorAll('.pagination a');

                // Loop through all pagination links
                paginationLinks.forEach(link => {
                    const parentLi = link.closest('li');
                    const isPageNumber = !parentLi.classList.contains('disabled') && !link.getAttribute('aria-label');

                    // Check if the link's href matches the current URL and it's a page number link
                    if (currentURL.includes(link.href) && isPageNumber) {
                        link.classList.add('active');
                    }
                });
            });




        </script>
    </body>
</html>
