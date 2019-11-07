package com.penelope.jugueteria.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.penelope.jugueteria.models.Juguete;

public class JuguetesManager {

	private Connection conn;

	public JuguetesManager(Connection conn) {
		this.conn = conn;
	}

	public List<Juguete> getAll() {

		String query = "SELECT * FROM juguete";
		Statement stmnt = null;
		ResultSet rs = null;
		List<Juguete> listaJuguetes = new ArrayList<Juguete>();

		try {
			stmnt = conn.createStatement();
			rs = stmnt.executeQuery(query);
			Juguete juguete = null;

			while (rs.next()) {
				juguete = new Juguete();
				juguete.setId(rs.getInt("id"));
				juguete.setNombre(rs.getString("nombre"));
				juguete.setPrecio(rs.getFloat("precio"));
				juguete.setDescripcion(rs.getString("descripcion"));
				juguete.setFechaRegistro(rs.getDate("fecha"));
				listaJuguetes.add(juguete);
			}
			// DatabaseConnector.closeConnection(conn);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (stmnt != null) {
				try {
					stmnt.close();
				} catch (SQLException e) {
					System.out.println("Fallo al cerrar el stmnt");
					e.printStackTrace();
				}
			}

		}
		return listaJuguetes;
	}

}
