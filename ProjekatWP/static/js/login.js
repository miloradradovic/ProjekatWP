$(document).ready(function(){
	
	$("#loginButton").click(function(){
		let korisnik = $("#login").serializeArray();
		var username = korisnik[0].value
		var password = korisnik[1].value
		console.log(username)
		if(username== "" || password== ""){
			alert("Oba polja moraju biti popunjena!")
		}else{
			$.ajax({
				url:"login",
				type:"post",
				data: JSON.stringify({korisnickoIme: username, lozinka:password}),
				success : function(data){
					if(data==false){
						alert("Korisnik "+username+" ne postoji!");
					}else{
						window.location.href = "/pregledVM.html";
					}
				}
			});
		}
	})
	
	
})