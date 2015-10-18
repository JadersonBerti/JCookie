$(document).ready(function(){
	
	function leitor(){
		var value = document.getElementById('reden');
		var linha = [];
		linha = value.split('\n');
		for(i = 0;i < linha.size;i++){
			var html = "<span>"+linha[i]+"</span><br/>";
		}
		return value = html;
	}
	
	function redirectIndex(){
		window.href("index.xhtml");	
	}

	
})