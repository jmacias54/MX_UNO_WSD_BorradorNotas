package mx.com.amx.unotv.uno.wsd.cleaner.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.amx.unotv.uno.wsd.cleaner.dto.CategoriaDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Qualifier("procesoDAO")
public class ProcesoDAO {
	
	private Logger LOG = Logger.getLogger(this.getClass().getName());
	
	@Value("${${ambiente}.database.db}")
	private String instDB ; 
	
	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	/**
	 * Obtiene la lista de categorias
	 * @return Lista de categorias
	 */
	public List<CategoriaDTO> getCategoria(){
		List<CategoriaDTO> resultado = new ArrayList<CategoriaDTO>();
		ArrayList<String> array = new ArrayList<String>();
		
		StringBuffer sb = new StringBuffer();
		String ESTATUS = "1";
		
		sb.append("SELECT C.FC_ID_CATEGORIA ");
		sb.append(",C.FC_ID_SECCION ");
		sb.append(",C.FC_DESCRIPCION ");
		sb.append(",C.FC_FRIENDLY_URL ");
		sb.append(",C.FI_REGISTROS ");
		sb.append(",C.FI_ESTATUS ");
		sb.append("FROM "+instDB+".UNO_MX_C_CATEGORIA C ");
		sb.append("WHERE C.FI_ESTATUS = 1 ");
		sb.append("ORDER BY C.FC_ID_SECCION, C.FC_ID_CATEGORIA ");
		
		try {
			
			String query = sb.toString();
			//array.add(ESTATUS);
			
			resultado = (ArrayList<CategoriaDTO>) jdbcTemplate.query(
					query, 
					array.toArray(), 
					new RowMapper<CategoriaDTO>() {
						public CategoriaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							CategoriaDTO dto = new CategoriaDTO();
							
							dto.setFcIdCategoria((rs.getString("FC_ID_CATEGORIA") != null && rs.getString("FC_ID_CATEGORIA").trim().length() > 0) ? rs.getString("FC_ID_CATEGORIA") : "");
							dto.setFcIdSeccion((rs.getString("FC_ID_SECCION") != null && rs.getString("FC_ID_SECCION").trim().length() > 0) ? rs.getString("FC_ID_SECCION") : "");
							dto.setFcDescripcion((rs.getString("FC_DESCRIPCION") != null && rs.getString("FC_DESCRIPCION").trim().length() > 0) ? rs.getString("FC_DESCRIPCION") : "");
							dto.setFcFriendlyUrl((rs.getString("FC_FRIENDLY_URL") != null && rs.getString("FC_FRIENDLY_URL").trim().length() > 0) ? rs.getString("FC_FRIENDLY_URL") : "");
							dto.setFiRegistros((rs.getString("FI_REGISTROS") != null && rs.getString("FI_REGISTROS").trim().length() > 0) ? rs.getString("FI_REGISTROS") : "");
							dto.setFiEstatus((rs.getString("FI_ESTATUS") != null && rs.getString("FI_ESTATUS").trim().length() > 0) ? rs.getString("FI_ESTATUS") : "");
							
							return dto;
						}
				
					});
		} catch (Exception e) {
			LOG.error("Excepcion en ["+this.getClass().getName()+".getCategoria] error: ",e);
		}
		
		return resultado;
	}
	
	
	/**
	 * Obtiene la lista de categorias
	 * @return Lista de categorias
	 */
	public int deleteNota(String categoria, String rowNumber){
		int resultado = 0;
		ArrayList<String> array = new ArrayList<String>();
		
		StringBuffer sb = new StringBuffer();
		
		/*sb.append("delete from (SELECT ROWNUMBER() OVER (ORDER BY N.FD_FECHA_PUBLICACION DESC) "); 
		sb.append("		FROM "+instDB+".UNO_MX_N_NOTA N ");
		sb.append("		WHERE N.FC_ID_CATEGORIA = ? ");
		sb.append(") as E(pos) "); 
		sb.append("where pos > ? ");*/
		
		sb.append(" DELETE FROM "+instDB+".UNO_MX_N_NOTA  ");
		sb.append(" WHERE FC_ID_CONTENIDO IN ((SELECT r.FC_ID_CONTENIDO FROM (SELECT @rownum:=@rownum+1 rank, n.*  ");
		sb.append(" FROM  "+instDB+".UNO_MX_N_NOTA N , (SELECT @rownum:=0) r  ");
		sb.append(" WHERE N.FC_ID_CATEGORIA = ? ) AS r ");
		sb.append(" WHERE   r.rank > ?) ) ");
		
		try {
			
			LOG.debug("query "+sb.toString());
			String query = sb.toString();
			array.add(categoria);
			array.add(rowNumber);
			
			resultado = jdbcTemplate.update( query, array.toArray());
			
		} catch (Exception e) {
			LOG.error("Excepcion en ["+this.getClass().getName()+".deleteNota] error: ",e);
		}
		
		return resultado;
	}

	/**
	 * Asigna el valor de jdbcTemplate.
	 * @param jdbcTemplate valor de jdbcTemplate.
	 */
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	

}
