<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Battleship Game</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="/js/jquery.js"></script>
    <script src="/js/main.js"></script> 
</head>

<body id="body">
    <a id="onlinePlayers">Players onlie: </a>  
    <a id="hiPlayer">Hi, <i><%=session.getAttribute("login")%></i></a>
    <h2 id="title" >Battleship</h2>

    <div id="help">
        <div id="error">Place the ship</div>
    </div>

    <div id="attacker">
        <jsp:include page="/jsp/battlefieldAttacker.jsp" flush="true" />
    </div>

    <button id="get-array" class="btn btn-success" type="submit" >Start Game</button>    
    
    <div id="protectorGlobal">
        <div id="protector">
            <jsp:include page="/jsp/battlefieldProtector.jsp" flush="true" />
        </div>
    </div>
</body>

<footer class="footer">
    <p class="author">Autor: Roman Kotsovskyi</p>
    <p class="author">2015, Kiev</p>
</footer>

</html>