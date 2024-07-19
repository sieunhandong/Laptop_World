
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Product</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta content="Free HTML Templates" name="keywords" />
        <meta content="Free HTML Templates" name="description" />
        <link href="img/favicon.ico" rel="icon" />
        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet" />
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet" />
        <link href="css/style.css" rel="stylesheet" />
        <style>
            .detail {
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .product {
                display: flex;
                flex-direction: row;
                align-items: center;
            }

            .product-img {
                margin-right: 20px;
            }

            .product-listing {
                flex-grow: 1;
            }
            .content {
                display: flex;
                flex-direction: column; /* Sắp xếp các phần tử theo chiều dọc */
                align-items: flex-start; /* Căn phần tử con sang trái */
            }

            .content > * {
                margin-bottom: 10px; /* Khoảng cách giữa các phần tử */
            }

            .price-block {
                display: flex; /* Sử dụng flexbox để căn giữa phần tử */
                align-items: center; /* Căn giữa theo chiều dọc */
            }

            .price, .del-price {
                margin-right: 10px; /* Khoảng cách giữa giá và giá gốc */
            }


        </style>
    </head>
    <body>
        <jsp:include page="Panner.jsp"></jsp:include>

        </div>
        <div class="row detail">
            <div class="product col-md-6">
                <div class="product-img">
                    <img src="${product.image_url}" alt="">

            </div>
            <div class="product-listing col-md-6">
                <div class="content row">
                    <h3 class="name" style="font-size: 25px">${product.name}</h3>
                    <p class="info">${product.description}</p>
                    <div class="price-block">
                        <p class="price mb-3" style="color: red"><strong>${product.price}</strong></p>
                        <p class="del-price mb-3"><del>$ ${Math.round(product.price * 1.25)}</del></p>
                    </div>

                    <div class="btn-and-rating-box">
                        <c:if test="${sessionScope.user.role_id == 1 || sessionScope.user.role_id == null }">
                            <a href="cart?service=addToCart&productId=${product.id}" style="color: white; background-color: black; padding: 10px; text-decoration: none; border-radius: 10px ">Add To Cart</a>

                        </c:if>
                        <c:if test="${sessionScope.user.role_id == 0}">
                            <div style="display: flex; justify-content: space-around; background-color: black; border-radius:10px ">
                                <div style="margin: 10px ;">
                                    <a href="manageProduct?service=requestUpdate&productId=${product.id}" style="color: white; padding: 10px ; font-family: 'Eczar', serif;">Update</a>
                                </div>
                                <div style="margin: 10px ;">
                                    <a href="manageProduct?service=requestDelete&productId=${product.id}" style="color: red; padding: 10px ; font-family: 'Eczar', serif;">Delete</a>
                                </div> 
                            </div>  

                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <jsp:include page="Footer.jsp" ></jsp:include>
</body>
</html>
