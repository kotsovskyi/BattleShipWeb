var ship_is_vertical=false,
	ship_size=4,
	ships_number={
		1:4,
		2:3,
		3:2,
		4:1
	},
	shipIndex = 0,
	length_ship = 0,
	ships_now={1:0,2:0,3:0,4:0},
	attacker = {},
	attackerBattlefield,
	protectorBattlefield,
	intervalId,
	previousText,
	shipCoordinates = {1:0, 2:0, 3:0, 4:0, 5:0, 6:0, 7:0, 8:0, 9:0, 10:0};

$(function(){
	var i;

	var cells=$('#battlefieldOpponent td');
	cells.each(function(index){
		var x=index%10,
			y=(index-x)/10;
		$(this).addClass('x'+x+' y'+y);
	});

	for(var key in ships_number){
		var ship='';
		for(i=0;i<key;i++)
			ship+='<td></td>';
		$('#ships').append('<li id="ship'+key+'">'+'</li>')
		$('#ship'+key+'').append('<table data-size="'+key+'"id="shipScript"><tr>'+ship+'</tr></table>')

	}
	
	$('#ships table').click(function(){
		$('#size').text(ship_size=$(this).data('size'));
		
		$('#ships table').removeClass('active');
		$(this).addClass('active');

	});

	$('#randomly').bind({
	  click: function() {
	  	randomPlacementShips();
	  },
	  mouseenter: function() {
	  	previousText = $('#error').text();
	    $('#error').text('Random placement of ships on the battlefield.');
	  },
	  mouseleave: function() {
	  	$('#error').text(previousText);	
	  }
	});

	function randomPlacementShips() {
		$('#field td').empty();	
		var cells=$('#field td'),
			i, j, jMax, count = -1,
			matrix = [],
			xHead, yHead, xTail, yTail, 
			direction = ['N', 'S', 'W', 'E'],
			length = [4, 3, 3, 2, 2, 2, 1, 1, 1, 1],
			size, of;

		cells.each(function(){
			cells.removeClass('ship');
			cells.removeClass('around');
		});	
		for(i = 0; i < 10; i++) {
			cells.filter('.numberShip'+i).removeClass('.numberShip'+i);
		}

		for(i = 0; i < 10; i++) {
			matrix[i] = [];
			for(j = 0; j < 10; j++) {	
				matrix[i][j] = j;
			}
		}

		for(i = 0; i < 10; i++) {
			do{
				do {
					yTail = getRandomNumber(0, 10);
				} while(matrix[yTail] == 0);
				
				xTail = getRandomNumber(0, matrix[yTail].length);
				of = getRandomNumber(0, 4);

				xHead = xTail;
				yHead = yTail;
				if(direction[of] == 'N') {
					yHead = yHead - (length[i] - 1);
				} else if(direction[of] == 'S') {
					yHead = yHead + (length[i] - 1);
				} else if(direction[of] == 'W') {
					xHead = xHead - (length[i] - 1);
				} else if(direction[of] == 'E') {
					xHead = xHead + (length[i] - 1);
				}
			} while((xHead < 0) || (xHead > 9) || (yHead < 0) || (yHead > 9) || (isMayPlaced(xHead, yHead, xTail, yTail) === false));

			if(xHead == xTail) {
	   			j = (yTail < yHead) ? yTail : yHead;
	   			size = Math.abs(yTail - yHead) + 1;
	   			jMax = j + size;
	   			for(j; j < jMax; j++) {
	   				cells.filter('.x'+xHead+'.y'+j).addClass('ship numberShip'+i);
	   			}
	   		} else if(yHead == yTail) {
	   			j = (xTail < xHead) ? xTail : xHead;
	   			size = Math.abs(xTail - xHead) + 1;
	   			jMax = j + size;
	   			for(j; j < jMax; j++) {
	   				cells.filter('.x'+j+'.y'+yHead).addClass('ship numberShip'+i);
	   			}
	   		}	

	   		var x,y,coords,selector,k;
	   		cells.filter('.ship').each(function(){
					x=get_x(this),
					y=get_y(this),
					coords=[[-1,-1],[-1,0],[-1,1],[0,-1],[0,0],[0,1],[1,-1],[1,0],[1,1]],
					selector=[],
					k;
				for(k=0;k<coords.length;k++){
					selector.push('.x'+(x+coords[k][0])+'.y'+(y+coords[k][1]));
				}
				cells.filter(selector.join(',')).not('.ship').addClass('around');
			});

			ships_now[length[i]]++;
			if(xHead == xTail) {
				if(yHead < yTail) {
					shipCoordinates[i+1] = [xHead,yHead,xTail,yTail,length[i]];
				} else {
					shipCoordinates[i+1] = [xTail,yTail, xHead, yHead,length[i]];
				}
			}else if(yHead == yTail) {
				if(xHead < xTail) {
					shipCoordinates[i+1] = [xHead,yHead,xTail,yTail,length[i]];	
				} else {
					shipCoordinates[i+1] = [xTail,yTail, xHead, yHead,length[i]];
				}
			}
		}
		isReadyStartGame();
		for(i = 1; i <= 4; i++) {
			ships_now[i] = 0;
		}
    }

    function isMayPlaced(xHead, yHead, xTail, yTail) {
   		var cells=$('#field td'), 
   			i, iMax, length;

   		if(xHead == xTail) {
   			i = (yTail < yHead) ? yTail : yHead;
   			length = Math.abs(yTail - yHead) + 1;
   			iMax = i + length;
 
   			for(; i < iMax; i++) {
   				if(cells.filter('.x'+xHead+'.y'+i).hasClass('around') ||  cells.filter('.x'+xHead+'.y'+i).hasClass('ship')){
   					return false;
   				}
   			}
   		} else if(yHead == yTail) {
   			i = (xTail < xHead) ? xTail : xHead;
   			length = Math.abs(xTail - xHead) + 1;
   			iMax = i + length;
   			
   			for(; i < iMax; i++) {
   				if(cells.filter('.x'+i+'.y'+yHead).hasClass('around') ||  cells.filter('.x'+i+'.y'+yHead).hasClass('ship')){
   					return false;
   				}	
   			}
   		}	
   		return true;
    }

    function getRandomNumber(min, max) {
    	return Math.floor(Math.random() * (max - min)) + min;
    }

    // When we click Start Game button
	$('#get-array').hide().click(function(){
		shipCoordinates.login = $('#hiPlayer i').text();
		$.ajax({
			  type: "POST",
			  url: "Controller",
			  data: {"arrObjects": JSON.stringify(shipCoordinates)},
			  success: function(response) {  
			  		var message = JSON.parse(response);

			  		var cells=$('#field td');
			  		cells.each(function(){
			  			cells.removeClass('around');
			  		});
			  		$('#error').text(message.message);
			  },
			  complete: function() {
				whoIsAttacker();
   		  	  }
		});
		$('#get-array').hide();
	});

	function whoIsAttacker() {
		var login = $('#hiPlayer i').text(),
			timeout_id;

	    $.ajax({
	      type:"POST",
	      url:"Controller",
	      data: {"login": JSON.stringify(login)},
	      success:function(data) {
	      	try {
	      		if(+data != 0) {
	      			attacker = JSON.parse(data);	
	      		}
	      	} catch(e) {
	      		if(e.name == 'SyntaxError') {
	      		}	
	      	}     	    	
	      },
	      complete: function() {
    		timeout_id = setTimeout(whoIsAttacker, 1000); 
    		if("attacker" in attacker) {
    			if(attacker.attacker === true) {
	      		$('#error').text("The game has begun. You can take a shot !!!");
	      		$('#protectorGlobal').css("opacity", "1");
	      		take_a_shot(); 
	      		clearTimeout(timeout_id);
	      	} else if (attacker.attacker === false) {
	      		$('#error').text("The game has begun. Wait for the opponent's shot !");
	      		change_role();
	      		clearTimeout(timeout_id);
	      	} 
    		}
   		  }
	    });
	}

	function take_a_shot() {
		var cells=$('#battlefieldOpponent td'),
			login = $('#hiPlayer i').text(),
			timeout_id,
			result = {};
		$('#protectorGlobal').css("opacity", "1");
		cells.mousedown(function(e){
			if(e.which==1){
				var coordinatesString = $(this).attr('class'),
					coordinatesOfShot = coordinatesString.charAt(1) + coordinatesString.charAt(4) + login,
					currentThis = $(this);
				$.ajax({
				  type: "POST",
				  url: "TakeShot",
				  data: {"coordinatesOfShot": JSON.stringify(coordinatesOfShot)},
				  	success: function(response){
					  	console.log("response >>> " + response.toString());
					  	if(+response != 0) {
					  		result = JSON.parse(response);
				  			if(result['attacker'] === true) {
				  				$(currentThis).html("<div>X</div>");  
								$(currentThis).addClass('wounded');
								
								if(result['isDestroyedShip'] === true) {
									cells.filter('.wounded').each(function(){
										var x=get_x(this),
										y=get_y(this),
										coords=[[-1,-1],[-1,0],[-1,1],[0,-1],[0,0],[0,1],[1,-1],[1,0],[1,1]],
										selector=[],
										i;

										for(i=0; i<coords.length; i++){
											selector.push('.x'+(x+coords[i][0])+'.y'+(y+coords[i][1]));
										}
										
										cells.filter(selector.join(',')).not('.wounded').addClass('around');
										cells.filter(selector.join(',')).not('.around').addClass('ship');
									});	
									cells.filter('.wounded.ship').removeClass('wounded');
								}
								$('#error').text(result['message']);

				  			} else {
				  				$(currentThis).html("<div>&bull;</div>"); 
								$(currentThis).addClass('around');
								$('#error').text(result['message']);
								$('#protectorGlobal').css("opacity", "0.35");
				  			}	
				      	
					  	}
				  	},
				  	complete: function() {
				  		if("attacker" in result) {
				  			if(result['attacker'] === true) {
				  				if('winner' in result) {
										$('#error').text('The game is completed. Congratulations ! You win !!!');
								} else {
									take_a_shot();	
								}
					  		} else {
					  			change_role();
					  		} 	
				  		}		  		
				  	}
				});
			}
		})
	}

	function change_role() {
		var login = $('#hiPlayer i').text(),
			cells=$('#field td'),
			result={}, hits = [], i, j;

		$.ajax({
			type: "POST",
			url: "TakeShot",
			data: {"login" : JSON.stringify(login)},
			success: function(response) {
				if(+response != 0) {
					result = JSON.parse(response);
					hits = result['hits'];	
				  	if(result['attacker'] === false) {
				  		$('#protectorGlobal').css("opacity", "0.35");
				  		place_hits(hits);  		
				  	}
				}	
			},
			complete: function() {
				if("attacker" in result) {
		  			if(result['attacker'] === true) {
		  				place_hits(hits);  	
		  				$('#protectorGlobal').css("opacity", "1");
		  				$('#error').text('Take a shot !!!');
			  			take_a_shot();			
			  		} else {
			  			if('loser' in result) {
				  			$('#error').text('The game is completed. You lose !!!');
				  		} else {	
			  				change_role();
				  		}				
			  		} 	
				}		 	
			}
		});	
	}

    function place_hits(hits){
    	var count = 0;
    	for(i = 0; i < 10; i++) {
  			for(j = 0; j < 10; j++) {
  				if(hits[count] === true) {
  					$('#field '+'.x'+i+'.y'+j+'.ship').html('<div>X</div>');
  					$('#field '+'.x'+i+'.y'+j).not('.ship').html('<div>&bull;</div>');
  				}
  				count++;
  			}
		}		
	}

	$('#ships table[data-size='+ship_size+']').click();

	function isReadyStartGame() {
		if(!isEquivalent(ships_number, ships_now)){
			$('#get-array').hide();	
		} else {
			$('#error').text('To start click "Start Game" button');
			$('#get-array').show();	
		}
	}
	
	function around_ships(){
		var cells=$('#field td');
		var need_coordinate_ship = [],
			all_coordinate_ship = [],
			popped, 
			first_elem = 0, 
			lengthOfShip = 0,
			second_elem = 0;
		cells.filter('.numberShip'+shipIndex+"").each(function(){
			var x=get_x(this),
				y=get_y(this),
				coords=[[-1,-1],[-1,0],[-1,1],[0,-1],[0,0],[0,1],[1,-1],[1,0],[1,1]],
				selector=[],
				i;
			for(i=0;i<coords.length;i++){
				selector.push('.x'+(x+coords[i][0])+'.y'+(y+coords[i][1]));
			}
			cells.filter(selector.join(',')).not('.ship').addClass('around');
			all_coordinate_ship.push(x,y);
		});
		
		for(i=0; i < all_coordinate_ship.length; i++) {
			if(i < 1){
				first_elem = all_coordinate_ship.shift();
				second_elem = all_coordinate_ship.shift();
				need_coordinate_ship.push(first_elem);	
				need_coordinate_ship.push(second_elem);		
				if(all_coordinate_ship.length == 0) {
					need_coordinate_ship.push(first_elem);	
					need_coordinate_ship.push(second_elem);
				}
			} else if(i == (all_coordinate_ship.length - 1)) {
				popped = all_coordinate_ship.pop();
				need_coordinate_ship.push(all_coordinate_ship.pop());
				need_coordinate_ship.push(popped);
			}
		}
		if(need_coordinate_ship[0] == need_coordinate_ship[2]){
			lengthOfShip = need_coordinate_ship[3] - need_coordinate_ship[1] + 1;
		} else {
			lengthOfShip = need_coordinate_ship[2] - need_coordinate_ship[0] + 1;
		}
		need_coordinate_ship.push(lengthOfShip);
		all_coordinate_ship = [];
		return need_coordinate_ship;
	}

	function cancel_around_ships(current_ship){
		if(current_ship.is('.around')){
			current_ship.removeClass('around');
		}
		if(current_ship.is('.ship')){
			if(current_ship.removeClass('ship').removeClass('numberShip'+shipIndex+"")){
				length_ship++;
			}
			var x=get_x(current_ship),
				y=get_y(current_ship),
				coords=[[-1,-1],[-1,0],[-1,1],[0,-1],[0,0],[0,1],[1,-1],[1,0],[1,1]],i;
			for(i=0;i<coords.length;i++){
				cancel_around_ships($('.x'+(x+coords[i][0])+'.y'+(y+coords[i][1])));
			}
		} 
	}

	function get_x(element){
		return /x(\d+)/.exec($(element).attr('class'))[1]*1;
	}
	function get_y(element){
		return /y(\d+)/.exec($(element).attr('class'))[1]*1;
	}

	var cells=$('#field td');
	cells.each(function(index){
		var x=index%10,
			y=(index-x)/10;
		$(this).addClass('x'+x+' y'+y);
	}).mousedown(function(e){
		if(e.which==1){
			var ship_cells=$('#field .hover');
			if(ship_cells.length){
				shipIndex++;
				ship_cells.removeClass('hover').addClass('ship').addClass('numberShip'+shipIndex+"");
				ships_now[ship_size]++;
				shipCoordinates[shipIndex] = around_ships();
			} else if($(this).is('.ship')){
				length_ship = 0;
				cancel_around_ships($(this));
				$('#ships table[data-size='+length_ship+']').click();
				ships_now[ship_size]--;
				shipIndex--;
			}
			isReadyStartGame();
		}else if(e.which==3){
			canselContexMenu();
			ship_is_vertical=!ship_is_vertical;
			$(this).mouseover();
			return false;
		}
	}).hover(function(){
		if(ships_now[ship_size]>=ships_number[ship_size]){
			$('#field .hover').removeClass('hover');
			$('#error').text('All ships of size '+ ship_size + ' are located on battlefield');
		}else{
			$('#error').empty();
			cells.removeClass('hover');
			var coord_var=(ship_is_vertical?'x':'y'),
				coord_const=(ship_is_vertical?'y':'x'),
				this_class=$(this).attr('class'),
				current={
					x:get_x(this),
					y:get_y(this)
				},
				ship_cells=cells.filter('.'+coord_var+current[coord_var]+':not(:lt('+current[coord_const]+'))'+':lt('+ship_size+')').not('.ship,.around');
			if(ship_cells.length==ship_size)
				ship_cells.addClass('hover');

		}
	});

	function canselContexMenu(){
		document.getElementById("field").oncontextmenu = function () { 
    		return false; 
		}
	}

	function isEquivalent(a, b) {
	    // Create arrays of property names
	    var aProps = Object.getOwnPropertyNames(a);
	    var bProps = Object.getOwnPropertyNames(b);

	    // If number of properties is different,
	    // objects are not equivalent
	    if (aProps.length != bProps.length) {
	        return false;
	    }

	    for (var i = 0; i < aProps.length; i++) {
	        var propName = aProps[i];

	        // If values of same property are not equal,
	        // objects are not equivalent
	        if (a[propName] !== b[propName]) {
	            return false;
	        }
	    }

	    // If we made it this far, objects
	    // are considered equivalent
	    return true;
	}

})








