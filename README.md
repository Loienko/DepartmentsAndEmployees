# DepartmentsAndEmployees
little task

	Есть сотрудники и департаменты.
	У департамента может быть много сотрудников, а может и не быть.
	На странице отображается список департаментов с кнопочками "Добавить/Редактировать/Удалить/Список сотрудников".
	При нажатии "Список" показываются сотрудники этого департамента с такими же кнопочками.
	Список - табличка, страница добавления/редактирования - набор текстфилдов.

	Технологии:
	1.БД - jdbc
	2.Controller - servlet
	3.View - jsp+el+jstl
	4.Валидация данных.
	5.Уникальность имени у департамента и email у пользователя.
	6.У сотрудника обязательно одно поле - числовое, одно - дата.
	7.Данные после валидации пропадать не должны, даже если они введены неправильно!
