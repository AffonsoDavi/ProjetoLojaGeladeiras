//cria o objeto COLDIGO, que sera usado como identificador do projeto
COLDIGO = new Object();

$(document).ready(function() {
	 //Crai uma constante com o valor da URI raiz do REST
	 COLDIGO.PATH = "/_ProjetoTrilhaWeb/rest/";
	
	$("header").load("/_ProjetoTrilhaWeb/pages/admin/general/header.html");
	$("footer").load("/_ProjetoTrilhaWeb/pages/admin/general/footer.html");
	
	//funcao para carregamento de paginas de conteudo, que 
	//recebe como parametro o nome da pasta com a pagina a ser carregada
	COLDIGO.carregaPagina = function(pagename){
		//Remove o conteudo criado na abettura de uma janela a ser carregada
		if($(".ui-dialog"))
		$(".ui-dialog").remove();
		//Limpa a tag section, excluindo todo o conteudo de dentro dela
		$("section").empty();
		//carrega a pagina solicitada dentro da tag section
		$("section").load(pagename+"/", function(response, status, info){
			if (status == "error"){
				var msg = "Houve um erro ao encontrar a página: "+ info.status + " - " + info.statusText;
				$("section").html(msg);
			}
		});
	}
	
	//Exibe os valores financeiros no formato da moeda Real
	COLDIGO.formataDinheiro = function(valor){
		return valor.toFixed(2).replace('.',',').replace(/(\d)(?=(\d{3})+\,)/g, "$1.");
	}
	//Define as configurações base de ua modal de aviso
	COLDIGO.exibirAviso =  function(aviso){
		var modal = {
			title: "Mensagem",
			height: 250,
			width: 400,
			modal: true, 
			buttons: {
				"OK": function(){
					$(this).dialog("close");
				}
			}
		}; 
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);
	};
});