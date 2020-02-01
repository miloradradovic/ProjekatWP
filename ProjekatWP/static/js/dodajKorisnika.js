$(document).ready(function(){
    
    $("#addUserButton").click(function(){
        $.ajax({
            type: "GET",
	        url: "/dobaviTrenutnogKorisnika",
	        data: 0,
	        contentType:"application/json",
	        dataType: "json",
	        success: function(data){
                if(data===0){
                    var username = $("#username").val();
                    var password = $("#password").val();
                    var ime = $("#name").val();
                    var prezime = $("#prezime").val();
                    var organizacija = $("#organizacija option:selected").text();
                    var uloga = $("#izborUloge option:selected").text();
                    if(username === "" || password === "" || ime === "" || prezime === ""){
                        alert("Neko od obaveznih polja je prazno!");
                    }else{
                        $.ajax({
                            type:"POST",
                            url:"/dobaviOrganizacijuPoID",
                            data:organizacija,
                            contentType:"application/json",
                            dataType:"json",
                            success:function(data){
                                var organizacija2 = data;
                                $.ajax({
                                    type:"POST",
                                    url:"/dobaviEnumUloga",
                                    data:uloga,
                                    contentType:"application/json",
                                    dataType:"json",
                                    success:function(data){
                                        var tip2 = data;
                                        $.ajax({
                                            type:"POST",
                                            url:"/dodajKorisnika",
                                            data:JSON.stringify({korisnickoIme:username,lozinka:password,ime:ime,prezime:prezime,organizacija:organizacija2,uloga:uloga2}),
                                            contentType:"application/json",
                                            dataType:"json",
                                            success:function(data){
                                                if(data===false){
                                                    alert("Dodavanje nije uspjelo!");
                                                }else{
                                                    alert("Uspjesno!");
                                                    window.location.href = "/pregledKorisnika.html";
                                                }
                                            }
                                        })
                                    }
                                })
                            }
                        })
                    }

                }else if(data===1){
                	var username = $("#username").val();
                    var password = $("#password").val();
                    var ime = $("name").val();
                    var prezime = $("prezime").val();
                    var organizacija = $("#organizacijaAdmin").text();
                    var uloga = $("#izborUloge option:selected").text();
                    if(username === "" || password === "" || ime === "" || prezime === ""){
                        alert("Neko od obaveznih polja je prazno!");
                    }else{
                    	$.ajax({
                            type:"POST",
                            url:"/dobaviOrganizacijuPoID",
                            data:organizacija,
                            contentType:"application/json",
                            dataType:"json",
                            success:function(data){
                                var organizacija2 = data;
                                $.ajax({
                                    type:"POST",
                                    url:"/dobaviEnumUloga",
                                    data:uloga,
                                    contentType:"application/json",
                                    dataType:"json",
                                    success:function(data){
                                        var tip2 = data;
                                        $.ajax({
                                            type:"POST",
                                            url:"/dodajKorisnika",
                                            data:JSON.stringify({korisnickoIme:username,lozinka:password,ime:ime,prezime:prezime,organizacija:organizacija2,uloga:uloga2}),
                                            contentType:"application/json",
                                            dataType:"json",
                                            success:function(data){
                                                if(data===false){
                                                    alert("Dodavanje nije uspjelo!");
                                                }else{
                                                    alert("Uspjesno!");
                                                    window.location.href = "/pregledKorisnika.html";
                                                }
                                            }
                                        })
                                    }
                                })
                            }
                        })
                    }
                }
            }
        });
    });
    
})