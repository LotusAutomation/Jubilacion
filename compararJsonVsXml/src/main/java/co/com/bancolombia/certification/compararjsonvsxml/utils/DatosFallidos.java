package co.com.bancolombia.certification.compararjsonvsxml.utils;

public class DatosFallidos {

	public DatosFallidos(String sUniversaID, String sNombreCampo, String sValorCampoJson, String sValorCampoXml) {
		this.sUniversaID = sUniversaID;
		this.sNombreCampo = sNombreCampo;
		this.sValorCampoJson = sValorCampoJson;
		this.sValorCampoXml = sValorCampoXml;
	}

	private String sUniversaID;
	private String sNombreCampo;
	private String sValorCampoJson;
	private String sValorCampoXml;

	public String getsUniversaID() {
		return sUniversaID;
	}

	public void setsUniversaID(String sUniversaID) {
		this.sUniversaID = sUniversaID;
	}

	public String getsNombreCampo() {
		return sNombreCampo;
	}

	public void setsNombreCampo(String sNombreCampo) {
		this.sNombreCampo = sNombreCampo;
	}

	public String getsValorCampoJson() {
		return sValorCampoJson;
	}

	public void setsValorCampoJson(String sValorCampoJson) {
		this.sValorCampoJson = sValorCampoJson;
	}

	public String getsValorCampoXml() {
		return sValorCampoXml;
	}

	public void setsValorCampoXml(String sValorCampoXml) {
		this.sValorCampoXml = sValorCampoXml;
	}

}
