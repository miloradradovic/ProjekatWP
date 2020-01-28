$(document).ready(function(){
    
    function izlistajOrganizacije(){
        $.ajax({
            type: "GET",
            url: "/dobaviTrenutnogKorisnika",
            data: 0,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
               //ako je data==0 onda je superadmin, ako je 1 onda je admin, ako je 2 onda je obicni korisnik
                if(data==0){
                   $("#organizacijaAdmin").hide();
                   $("#izborOrganizacije").empty();
                   $.ajax({
                       type:"GET",
                       url: "/dobaviSveOrganizacije",
                       data:0,
                       contentType:"application/json",
                       dataType:"json",
                       success:function(data){
                           //vratice listu svih organizacija iz servera
                           data.array.forEach(element => {
                               $("#izborOrganizacije").append($("<option>").text(element.ime))
                           });
                       }
                   })
               } 
            }
        })
        
    };
    
    function izlistajTipove(){
    	
    };

    $("#racSelektor").empty()
	$("#tabelaRacuna").find("tr").each(function(){
		var tds = $(this).find('td')
		brojRac = tds.eq(0).text()
    	isAktive = tds.eq(6).text()
		if(isAktive==true){
			$("#racSelektor")
				.append($("<option>")
					.text(brojRac)
				)
		}
	})

    
})