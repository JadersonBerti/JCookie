package br.com.projectjersey.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.projectjersey.errodto.ErroDTO;

@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Throwable> {

		public ExceptionMapperImpl() {
		}

		@Override
		public Response toResponse(Throwable erro) {
			ErroDTO erroDTO = null;
			if (erro instanceof RestException) {
				erroDTO = new ErroDTO(((RestException) erro).getCodigoErro(),
						erro.getMessage());
			} else {
				erroDTO = new ErroDTO(0, erro.getMessage());
			}

			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(erroDTO)
					.build();
		}
}	
