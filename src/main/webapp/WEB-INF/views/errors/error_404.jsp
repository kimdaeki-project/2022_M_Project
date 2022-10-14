<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    ul{
        padding-left: 0px;
    }
    li{
        list-style: none;
        
    }

    #errorBtn{
        width: 378px;
        height: 72px;
        background-color: #edffd0;
    }
</style>
<c:import url="../template/headerHTML.jsp"></c:import>
</head>
<img src="../../../resources/kdy/images/realLodo.jpg" onclick="location='../../../../../'" style="width: 89px; height: 50px; margin: 0px 90px 0px 0px; cursor: pointer;" href="../../" alt="">
<body>
	<h1>KOR)죄송합니다 페이지 오류입니다. 페이지가 제거되었거나, 이름이 변경 혹은 일시적으로 사용 중지되었습니다.</h1>

    <h3>ENG) Sorry, page error The page has been removed, renamed, or temporarily disabled.</h3>
    <h3>JAP) 申し訳ありません。ページエラーです。ページが削除されたか、名前が変更または一時的に無効になりました。</h3>
    <h3>Chinese) 对不起 是页面错误 页面已删除、重命名或暂时停止使用。</h3>
    <h3>Russian) Извините, ошибка на странице Страница была удалена, переименована или временно отключена.</h3>

    <br><br>

    <h3>다른 방법을 안내해 드리겠습니다!</h3>
    <h3>I'll tell you another way!</h3>
    
    <br>

    <ul>
        <li>1.URL을 다시 한 번 확인해서 시도해 주세요.</li>
        <li>
            2.
            <a href="/" class="orange_a" style="color: #9BF706; text-decoration: none;">메인페이지</a>
            "로 다시 돌아가주세요."
        </li>
    </ul>


    <div style="text-align: center;">
        <button type="button" id="errorBtn" class="btn" onclick="location='../../../../../../../../'" style="cursor: pointer; font-size: 15px; float: center; border-radius: 40px;">메인으로 돌아가기</button>
    </div>

</body>
<c:import url="../template/footerHTML.jsp"></c:import>
</html>