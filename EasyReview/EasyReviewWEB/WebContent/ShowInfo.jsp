<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- ===== CSS ===== -->
<link rel="stylesheet" href="css/styles.css">

<title>Sidebar sub menus</title>
</head>
<body id="body-pd">
	<div class="l-navbar" id="navbar">
		<nav class="nav">
			<div>
				<div class="nav__brand">
					<ion-icon name="menu-outline" class="nav__toggle" id="nav-toggle"></ion-icon>
					<a href="#" class="nav__logo">EasyReview</a>
				</div>
				<div class="nav__list">
					<a href="#" class="nav__link active"> <ion-icon
							name="home-outline" class="nav__icon"></ion-icon> <span
						class="nav__name">HomePage</span>
					</a> <a href="#" class="nav__link"> <ion-icon
							name="chatbubbles-outline" class="nav__icon"></ion-icon> <span
						class="nav__name">QuestionnairePage</span>
					</a>

					<div class="nav__link collapse">
						<ion-icon name="folder-outline" class="nav__icon"></ion-icon>
						<span class="nav__name">LeaderboardPage</span>

						<ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>

						<ul class="collapse__menu">
							<a href="#" class="collapse__sublink">Sottovoce1</a>
							<a href="#" class="collapse__sublink">Sottovoce2</a>
							<a href="#" class="collapse__sublink">Sottovoce3</a>
						</ul>
					</div>

					<a href="#" class="nav__link"> <ion-icon
							name="pie-chart-outline" class="nav__icon"></ion-icon> <span
						class="nav__name">Voce4</span>
					</a>
					<div class="nav__link collapse">
						<ion-icon name="people-outline" class="nav__icon"></ion-icon>
						<span class="nav__name">Voce5</span>

						<ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>

						<ul class="collapse__menu">
							<a href="#" class="collapse__sublink">Sottovoce4</a>
							<a href="#" class="collapse__sublink">Sottovoce5</a>
							<a href="#" class="collapse__sublink">Sottovoce6</a>
						</ul>
					</div>
					<a href="#" class="nav__link"> <ion-icon
							name="settings-outline" class="nav__icon"></ion-icon> <span
						class="nav__name">Voce6</span>
					</a>
				</div>
			</div>

			<a href="#" class="nav__link"> <ion-icon name="log-out-outline"
					class="nav__icon"></ion-icon> <span class="nav__name">Log
					Out</span>
			</a>
		</nav>
	</div>

	<h1>Componentes</h1>
	<form action="GoToHomePage" method="get">
		<input type="submit" value="ProvaProdotto">
	</form>
	<form action="GoToQuestionnairePage" method="get">
		<input type="submit" value="ProvaQuestionario">
	</form>
		<form action="GoToLeaderboardPage" method="get">
		<input type="submit" value="ProvaLeaderboard">
	</form>
	</form>
		<form action="GoToQuestionnairePage" method="post">
		<input type="submit" value="ProvaAnswer">
	</form>

	<!-- ===== IONICONS ===== -->
	<script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>

	<!-- ===== MAIN JS ===== -->
	<script src="js/main.js"></script>
</body>
</html>