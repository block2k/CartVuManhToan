<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <title>W3.CSS Template</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        /* Full-width input fields */
        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        /* Extra styles for the cancel button */
        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        /* Center the image and position the close button */
        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;
            position: relative;
        }

        img.avatar {
            width: 40%;
            border-radius: 50%;
        }

        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 60px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        /* The Close Button (x) */
        .close {
            position: absolute;
            right: 25px;
            top: 0;
            color: #000;
            font-size: 35px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: red;
            cursor: pointer;
        }

        /* Add Zoom Animation */
        .animate {
            -webkit-animation: animatezoom 0.6s;
            animation: animatezoom 0.6s
        }

        @-webkit-keyframes animatezoom {
            from {-webkit-transform: scale(0)} 
            to {-webkit-transform: scale(1)}
        }

        @keyframes animatezoom {
            from {transform: scale(0)} 
            to {transform: scale(1)}
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }
            .cancelbtn {
                width: 100%;
            }
        }
    </style>
    <jsp:useBean id="dal" scope="page" class="dal.DAO" />
    <body class="w3-light-grey">
        <!-- Overlay effect when opening sidebar on small screens -->
        <!-- !PAGE CONTENT! -->
        <div class="" >
            <!--login form-->
            <header class="w3-container" style="padding-top:22px">
                <h5><b><i class="fa fa-dashboard"></i> Shop Vu Manh Toan</b></h5>
            </header>

            <div class="w3-container">

                <c:if test="${listC!=null}">
                    <button>
                        <a href="Product" style="text-decoration: none;">
                            Show all Product
                        </a>
                    </button>
                    <table class="w3-table w3-striped w3-bordered w3-border w3-hoverable w3-white">
                        <tr>
                            <td>ID</td>
                            <td>Name</td>
                            <td>Quantity</td>
                            <td>Price</td>
                            <td>Total</td>
                            <td>Action</td>
                        </tr>
                        <c:forEach var="o" items="${listC}">
                            <tr>
                                <td>${o.id}</td>
                                <td>${o.name}</td>
                                <td>
                                    <input form="formUpdate" type="number" min="1" name="${o.genId()}" value="${o.quantity}" />
                                </td>
                                <td>
                                    ${o.getPriceVND()}
                                </td>
                                <td>
                                    ${o.getTotalPriceVND()}
                                </td>
                                <td>
                                    <form action="Product?action=RemoveProductFromCart" method="post">
                                        <input type="hidden" name="cartId" value="${o.id}" />
                                        <input type="submit" value="Remove" />
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <form id="formUpdate" action="Product?action=updateQuantity" method="post">
                                    <input type="submit" value="Update" />
                                </form> 
                            </td>
                            <td></td>
                            <td></td>
                            <td>Total: </td>
                            <td>
                                ${dal.getTotalCartPrice(1)}
                            </td>
                            <td>
                                <form action="Product?action=RemoveProductFromCart" method="post">

                                    <input type="submit" value="Remove All" />
                                </form>
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${listP!=null}">
                    <h5>
                        <button>
                            <a href="Product" style="text-decoration: none;">
                                Show all Product
                            </a>
                        </button>
                    </h5>
                    <a href="Product?action=Cart" class="w3-bar-item w3-button"><i class="fa fa-shopping-cart"></i></a>
                    <table class="w3-table w3-striped w3-bordered w3-border w3-hoverable w3-white">
                        <c:forEach var="o" items="${listP}">
                            <tr>
                                <td>${o.name}</td>
                                <td>${o.getPriceVND()}</td>
                                <td>${o.description}</td>
                                <td>
                                    <form action="Product?action=AddToCart" method="post">
                                        <input type="hidden" name="productId" value="${o.id}" />
                                        <input type="hidden" name="productPrice" value="${o.price}" />
                                        <button type="submit">Add to cart</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
            <hr>
        </div>

        <script>
            // Get the Sidebar
            var mySidebar = document.getElementById("mySidebar");

            // Get the DIV with overlay effect
            var overlayBg = document.getElementById("myOverlay");

            // Toggle between showing and hiding the sidebar, and add overlay effect
            function w3_open() {
                if (mySidebar.style.display === 'block') {
                    mySidebar.style.display = 'none';
                    overlayBg.style.display = "none";
                } else {
                    mySidebar.style.display = 'block';
                    overlayBg.style.display = "block";
                }
            }

            // Close the sidebar with the close button
            function w3_close() {
                mySidebar.style.display = "none";
                overlayBg.style.display = "none";
            }
        </script>

    </body>
</html>
