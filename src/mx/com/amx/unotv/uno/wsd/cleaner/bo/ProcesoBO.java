package mx.com.amx.unotv.uno.wsd.cleaner.bo;

import mx.com.amx.unotv.uno.wsd.cleaner.dao.ProcesoDAO;
import mx.com.amx.unotv.uno.wsd.cleaner.dto.ResponseCategoriaDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("procesoBO")
public class ProcesoBO {
	
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	
	ProcesoDAO procesoDAO;
	
	/**
	 * Obtiene la lista de categorias
	 * @return Lista de categorias
	 */
	public ResponseCategoriaDTO getCategoria(){
		
		ResponseCategoriaDTO dto = new ResponseCategoriaDTO();
		
		try {
			dto.setLista(procesoDAO.getCategoria());
		} catch (Exception e) {
			LOG.error("Ocurrio un error en [" + this.getClass().getName() + ".getCategoria] error:" + e.getMessage());
		}
		
		return dto;
	}
	
	/**
	 * Depura las notas por categoria
	 * @param categoria Id de la categoria
	 * @param rowNumber rowNumber que indica a partir de que registro depurar
	 * @return Numero de registros depurados
	 */
	public int deleteNota(String categoria, String rowNumber){
		
		int respuesta = 0;
		
		try {
			respuesta = procesoDAO.deleteNota(categoria, rowNumber);
		} catch (Exception e) {
			LOG.error("Ocurrio un error en [" + this.getClass().getName() + ".deleteNota] error:" + e.getMessage());
		}
		
		return respuesta;
	}

	/**
	 * Asigna el valor de procesoDAO.
	 * @param procesoDAO valor de procesoDAO.
	 */
	@Autowired
	public void setProcesoDAO(ProcesoDAO procesoDAO) {
		this.procesoDAO = procesoDAO;
	}
	
}
