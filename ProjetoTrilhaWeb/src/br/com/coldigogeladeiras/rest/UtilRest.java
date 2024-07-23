package br.com.coldigogeladeiras.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

public class UtilRest {
	
	/*Abaixo o metodo responsavel por enviar a resposta ao cliente sobre 
	*a transacao realizada (inclusao, colsulta, edicao ou exclusao) caso
	*ela seja realizada com sucesso.
	*repare que o metodo em questao aguarda que seja repassado um
	*conteudo 	que sera referenciado por um objeto chamado result.
	*/
	public Response buildResponse(Object result) {
		try {
		/* retorna o objeto de resposta com status 200 (ok), tendo 
		 * em seu corpo o objetivo valorResposta (que consiste no
		 * objeto result convertido para JSON).
		 */
		String valorResposta = new Gson().toJson(result);
		return Response.ok(valorResposta).build();
		
	}catch (Exception ex) {
		ex.printStackTrace();
		//se algo der errado acima, cria Response de erro
		return this.buildErrorResponse(ex.getMessage());
	}
}
	
	/*Abaixo o metodo responsavel por enviar a resposta ao cliente sobre 
	*a transacao realizada, inclusão, consulta, edição ou exclusao.ao
	*cliente, nao realizados com sucesso, ou seja , que contenha algum 
	*erro.
	*repare que o metodo em questao aguarda que seja repassado um
	*conteudo que sera referenciado por um objeto chamado rb.
	*/
	public Response buildErrorResponse(String str) {
		// abaixo o objeto rb recebe o status do erro.
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		
		/*Define a entidade (objeto), que nesse caso é uma 
		 * mensagem que sera retornado para o cliente.
		 */
	
		rb = rb.entity(str);
		
		/* Define o tipo de retorno desta entidade(objeto), no 
		 * caso é definido como texto simples
		 */
		
		rb = rb.type("text/plain");
		
		/*Retorna o objeto de resposta com status 500(erro),
		 * junto com a string contendo a mensagem de erro.
		 */
		return rb.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}