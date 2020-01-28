$(document).ready(function(){

function getFormData($form){
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};
	
	$.map(unindexed_array, function(n, i){
		indexed_array[n['name']] = n['value'];
	});
	
	return indexed_array;
}

function pregledVM(){
	var user = localStorage.getItem("USER")
	console.log("woho")
	$.ajax({
		type: "POST",
		url: "pregledVM",
		data: 0,
		contentType: "application/json",
		dataType: "json",
		success: function(data){
			if(data == false){
				alert("Morate biti ulogovani da biste pristupili ovoj stranici")
				location.href = "/index.html"
			}else{
				if (user.uloga == TipKorisnika.SuperAdmin){
					data.forEach(element => {
						$("#tabelaVM").append($("<tr>")
								  	  	.append($("<td>")
								  				.text(element.ime))
							  		  	.append($("<td>")
							  		  			.text(element.kategorija.brojJezgara))
						  		  	  	.append($("<td>")
						  		  	  			.text(element.kategorija.RAM))
					  		  	  	  	.append($("<td>")
					  		  	  	  			.text(element.kategorija.GPU))
				  		  	  	  	  	.append($("<td>")
				  		  	  	  	  			.append($("<a>")
				  		  	  	  	  				.attr("href", "#")
				  		  	  	  	  				.attr("ime", element.ime)
				  		  	  	  	  				.attr("onclick", "/izmenaVM.html")
				  		  	  	  	  				.text("Izmeni")))
			  		  	  	  	  	)
					})
				}else if (user.uloga == TipKorisnika.Admin){
					$("#organizacija").hide();
					data.forEach(element => {
						$("#tabelaVM").append($("<tr>")
								  	  	.append($("<td>")
								  				.text(element.ime))
							  		  	.append($("<td>")
							  		  			.text(element.kategorija.brojJezgara))
						  		  	  	.append($("<td>")
						  		  	  			.text(element.kategorija.RAM))
					  		  	  	  	.append($("<td>")
					  		  	  	  			.text(element.kategorija.GPU))
				  		  	  	  	  	.append($("<td>")
				  		  	  	  	  			.append($("<a>")
				  		  	  	  	  				.attr("href", "#")
				  		  	  	  	  				.attr("ime", element.ime)
				  		  	  	  	  				.attr("onclick", "/izmeniVM.html")
				  		  	  	  	  				.text("Izmeni")))
			  		  	  	  	  	)
					})
					
				}
			}
		}
	})
}
})		