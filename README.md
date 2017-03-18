# GitHub-Reader

<center><h1>Тестовое задание в Polonium Arts </h1></center>

<p>Данная работа выполнена в начале 2017 года.</p>
<p>Взаимодействие c Github выполнена по средствам <a href="https://developer.github.com/v3/repos/">RestAPI</a>.</p>

Приложение отображает список GitHub-репозиториев произвольного пользователя и выводи основые данные о каждом репозитории:
<ul>
<li> имя или полное имя репозитория в случае, если пользователь не является владельцем;</li>
<li> описание;</li>
<li> дата и время создания и обновления;</li>
<li> количество звёзд;</li>
<li> язык.</li>
</ul>
     
<p>Данные запроса и результата кэшируются в базу данных, которая реализована по средствам <a href="https://github.com/Raizlabs/DBFlow">DBFlow</a>.</p>

<p><p>Скриншот главного окна приложения:</p>
<img src="https://cloud.githubusercontent.com/assets/11809712/24075180/aea64c56-0c27-11e7-8789-a5f221b70b6c.jpg" alt="" widht="250" height="600"/></p>
<p><p>Скриншот вывода репозиториев пользователя:</p>
<img src="https://cloud.githubusercontent.com/assets/11809712/24075201/05f1b0f4-0c28-11e7-98a7-61947a2f5293.jpg" alt="" widht="250" height="600"/></p>

