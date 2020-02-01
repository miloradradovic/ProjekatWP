$(document).ready(function()
{
    $("#izmeni").click(function(){
        var novoIme = $("#ime").val();
        console.log(novoIme)
        var novaKategorija = $("#kategorijaID option:selected").text();
        console.log(novaKategorija)
        var upaljene = [];
        var ugasene = [];
        $("#tabelaBezNaslova").find("tr").each(function()
		{
            var tds = $(this).find("td")
            var inputs = $(tds).find("input")
            if (inputs.length!==0)
            {
                upaljene.push(inputs[0].defaultValue);
                ugasene.push(inputs[1].defaultValue);
            }
            else 
            {
                console.log(inputs)
            }
            
        })
		console.log(upaljene)
        $.ajax({
            type: "POST",
            url: "noviPodaci",
            data: JSON.stringify({novoime:novoIme,novakat:novaKategorija,noveupal: upaljene,noveugas: ugasene}),
            contentType: "application/json",
            dataType: "json",
            success: function(data){
                //window.location.href = "pregledVM.html";
            }
        })
    })
})
