/**
 * Copyright (c) 2015 América Móvil, S.A. de C.V. Todos los derechos reservados.
 * Este software contiene información confidencial propiedad de
 * América Móvil por lo cual no puede ser reproducido, distribuido o
 * alterado sin el consentimiento previo de América Móvil.
 */

/**
 * Declaración de paquete.
 */
package mx.com.amx.unotv.uno.wsd.cleaner.services;

import mx.com.amx.unotv.uno.wsd.cleaner.bo.ProcesoBO;
import mx.com.amx.unotv.uno.wsd.cleaner.dto.ResponseCategoriaDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <P>
 * Services.java is a member of project MX_UNO_WSD_BorradorNotas.
 * <P>
 * Created on 28/10/2015 at 11:17:45.
 * @version 1.0 28/10/2015
 * @since JDK1.6 
 * @author Ruth Mota
 */
@Controller
@RequestMapping("/all")
public class Services {

	private Logger LOG = Logger.getLogger(this.getClass().getName());
	
	ProcesoBO procesoBO;
	
	/**
	 * Obtiene la lista de categorias
	 * @return Lista de categorias
	 */
	@RequestMapping(value="get/categoria" , method=RequestMethod.POST , headers="Accept=application/json; charset=utf-8;")
	@ResponseBody
	public ResponseCategoriaDTO getCategoria(){
		
		LOG.debug("Ejecuta getCategoria");
		
		ResponseCategoriaDTO dto = new ResponseCategoriaDTO();
		
		try {
			dto = procesoBO.getCategoria();
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
	@RequestMapping(value="delete/nota/{categoria}/{rowNumber}", method=RequestMethod.POST, headers="Accept=application/json; charset=utf-8;")
	@ResponseBody
	public int deleteNota(@PathVariable String categoria, @PathVariable String rowNumber){
		
		LOG.debug("Ejecuta deleteNota. categoria:" + categoria + " rowNumber:" + rowNumber);
		
		int respuesta = 0;
		
		try {
			respuesta = procesoBO.deleteNota(categoria, rowNumber);
		} catch (Exception e) {
			LOG.error("Ocurrio un error en [" + this.getClass().getName() + ".deleteNota] error:" + e.getMessage());
		}
		
		return respuesta;
	}

	
	/**
	 * Asigna el valor de procesoBO.
	 * @param procesoBO valor de procesoBO.
	 */
	@Autowired
	public void setProcesoBO(ProcesoBO procesoBO) {
		this.procesoBO = procesoBO;
	}
	
	
}
