
function checkLogin(iLogin, iPassword) {
  return (iLogin == username && iPassword == password)
}

// При запуске
function login() {
  if (logged) {
    $('#login').hide();
    $('#logged').show();
    $('#username').text(username);
    readFromDb();
  } else {
    var iLogin = $('#login-input').val(),
        iPassword = $('#password-input').val();
    if (checkLogin(iLogin, iPassword)) {
      $('#login').hide();
      $('#logged').show();
      $('#username').text(username);
      readFromDb();
    }
  }
}

// Функция запускается в readFromDb и при смене категории
function readTasksFromDb() {
  for (var element in DB.categoryList) {
    $('#catnav').append(
      '<li class="nav-item">' +
      '<a class="nav-link" href="#" data-cat-id="' + element + '">' + DB.categoryList[element] + '</a>' +
      '</li>'
    )
    $('#add-todo-category').append(
      '<option value="' + element + '">' + DB.categoryList[element] + '</option>'
    )
    addCatId = element;
  };
  // Запросить из БД записи с нужной категорией
  DB.tasks.forEach(function(element) {
    addTaskId = element[0];
    $('#TasksList' + (element[4]?'Done':'')).prepend(
      '<div class="card" data-cat="' + element[2] + '" data-todo-id="' + addTaskId + '">' +
      '<div class="card-body">' +
      '<h5 class="card-title">' + element[1] + '</h5>' +
      '<h6 class="card-subtitle mb-2 text-muted">' +
      '<span class="badge badge-pill badge-secondary">' +
      (element[2]!='0' ? DB.categoryList[element[2]] : 'Без категорії') + '</span></h6>' +
      '<p>' + element[3] + '</p>' +
      (!element[4]?'<a href="#" class="card-link doneTask">Виконати</a>':'') +
      '</div></div>'
    );
  });
}

// Запускается после логина
function readFromDb() {
  // Подключение к БД 
  // Добавить весь список
  // Добавить все планы
  readTasksFromDb();
  
  DB.groceries.forEach(function(element) {
    addGroceryId = element[0];
    $('#groceries' + (element[2]?'done':'')).prepend(
      '<li class="list-group-item d-flex justify-content-between align-items-center">' +
      element[1] +
      (!element[2]?'<button type="button" class="btn btn-sm mvGrocery">×</button>':'') + '</li>'
    );
  });
  
  DB.plans.forEach(function(element) {
    addPlansId = element[0];
    $('#'+(element[1]?'short':'long')+'term'+(element[3]?'done':'')).prepend(
      '<li class="list-group-item d-flex justify-content-between align-items-center">' +
      element[2] +
      (!element[3]?'<button type="button" class="btn btn-sm mvPlan">×</button>':'') + '</li>'
    );
  });
}

$(document).ready(function () {
  $('#catnav').on('click', 'a', function(e){
    $('#catnav a.active').removeClass('active');
    $(this).addClass('active');
    if (!e.target.hasAttribute('data-cat-id')) {
      catShown = -1;
      $('#TasksList .card, #TasksListDone .card').show();
    } else {
      catShown = e.target.getAttribute('data-cat-id');
      $('#TasksList .card, #TasksListDone .card').hide();
      $('#TasksList .card[data-cat=' + $(this).attr('data-cat-id') + ']').show();
      $('#TasksListDone .card[data-cat=' + $(this).attr('data-cat-id') + ']').show();
    }
  });
});

function addCategory () {
	fetch('/categories/add', {
		method: 'POST',
		headers: {
            'Content-Type': 'application/json',
        },
		body: JSON.stringify({
			name: $('#add-cat-title').val(),
			userId,
		}),
	}).then(response => response.json())
	.then(category => {
		$('#catnav').append(
			    '<li class="nav-item">' +
			    '<a class="nav-link" href="#" data-cat-id="' + category.id + '">' + category.name + '</a>' +
			    '</li>'
			  )
			  $('#add-todo-category').append(
			    '<option value="' + category.id + '">' + category.name + '</option>'
			  )
	});
}

function addTodo() {
  /*
    Обратиться к серверу для добавления записи в базу и получения её id (далее — „Добавить запись в БД“)
  */
	fetch('/tasks/add', {
		method: 'POST',
		headers: {
            'Content-Type': 'application/json',
        },
		body: JSON.stringify({
			name: $('#add-todo-title').val(),
			description: $('#add-todo-details').val(),
			userId,
			categoryId: $('#add-todo-category').val() != '0' ? Number($('#add-todo-category').val()) : null,
		}),
	}).then(response => response.json())
	.then(task => {
		$('#TasksList').prepend(
			    '<div class="card" data-cat="' + $('#add-todo-category').val() + '" data-todo-id="' + task.id + '">' +
			    '<div class="card-body">' +
			    '<h5 class="card-title">' + task.name + '</h5>' +
			    '<h6 class="card-subtitle mb-2 text-muted">' +
			    '<span class="badge badge-pill badge-secondary">' + $('#add-todo-category').children()[Number($('#add-todo-category').val())].text + '</span></h6>' +
			    '<p>' + task.description + '</p><a href="#" class="card-link doneTask">Виконати</a></div></div>'
			  )
			  $('#add-todo-title').val('');
			  $('#add-todo-category').val(0);
			  $('#add-todo-details').val('');
	});
}

function addGroceries() {
  // Добавить запись в БД
  
	fetch('/purchases/add', {
		method: 'POST',
		headers: {
            'Content-Type': 'application/json',
        },
		body: JSON.stringify({
			name: $('#add-groceries-title').val(),
			done: false,
			userId,
		})
	}).then(res => res.json())
		.then(purchase => {
			$('#groceries').prepend(
				    '<li class="list-group-item d-flex justify-content-between align-items-center" data-purchases-id="' + purchase.id + '">' +
				    purchase.name + '<button type="button" class="btn btn-sm mvGrocery">×</button></li>'
				  );
				  $('#add-groceries-title').val('');
			});
}

function addPlan() {
  // Добавить запись в БД
	
	fetch( "/plans/add", {
		method: "POST",
		headers: {
            'Content-Type': 'application/json',
        },
		body: JSON.stringify({
			shortterm: $('input[name="perspective"]:checked').val() === "shortterm" ? false : true,
			name: $('#add-plans-title').val(),
			userId,
			done: false,
		})
	}
	).then(res => res.json()).then(plans => {
		 $('#'+$('input[name="perspective"]:checked').val()).prepend(
				    '<li class="list-group-item d-flex justify-content-between align-items-center" data-plans-id="' + plans.id + '">' +
				    $('#add-plans-title').val() + '<button type="button" class="btn btn-sm mvPlan">×</button></li>'
				  );
				  $('#add-plans-title').val('');
				  $('input[name="perspective"][value="shortterm"]').prop('checked', true);
	}); 
}

// removeTodo
$(document).ready(function () {
  $('#TasksList').on('click', '.doneTask', function(){
	  fetch('/tasks/' + $(this).parent().parent().data('todo-id') + '/archive', {
		  method: 'POST'
	  }).then(res => {
		    $(this).parent().parent().appendTo('#TasksListDone');
		    $(this).remove();
	  });
	  
  });
  // Отметить выполненным в базе
  $('#grocerylist').on('click', '.mvGrocery', function(){
	  fetch('/purchases/' + $(this).parent().data('purchases-id') + '/archive', {
		  method: 'POST'
	  }).then(res => {
		  $(this).parent().parent().appendTo('#groceriesdone');
		    $(this).remove();
		  
	  })
    $(this).parent().appendTo('#groceriesdone');
    $(this).remove();
  });
  // Отметить выполненным в базе
  $('#plans').on('click', '.mvPlan', function(){
	  fetch('/plans/' + $(this).parent().data('plans-id') + '/archive', {
		  method: 'POST'
	  }).then(res => {
		  	if ($(this).parent().parent().attr('id') === 'shortterm') {
		  		$(this).parent().appendTo('#shorttermdone');
		  	} else {
		  		$(this).parent().appendTo('#longtermdone');
		  	}
		    $(this).remove();	
	  });
  });
});
