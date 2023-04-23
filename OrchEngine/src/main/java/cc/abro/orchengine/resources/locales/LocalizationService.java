package cc.abro.orchengine.resources.locales;

import cc.abro.orchengine.context.EngineService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.StringReader;
import cc.abro.orchengine.resources.ResourceLoader;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * Сервис локализации. Пока что не умеет в загрузку локалей из файла, но переводики можно вписать в DEFAULT_LOCALE.:<br><br>
 * <code>
 * localize("locale.id") -> ru <br>
 * localize("sample.text") -> SAMPLE_TEXT <br>
 * localize("sample.format") -> SAMPLE{0}TEXT <br>
 * localize("sample.format", "*") -> SAMPLE*TEXT <br>
 * localize("explicit.error") -> [[explicit.error]]
 * </code><br>
 * <b style="color: red;">Потом допилю, не плачь</b>
 */
@Log4j2
@EngineService
public class LocalizationService {
	/**
	 * Список локалей id -- локаль
	 */
	private final Map<String, Localization> locales = new HashMap<>();
	private Localization currentLocale;


	public LocalizationService() {
		// TODO: доделать сканирование локалей
		try {
			List<String> scan = ResourceLoader.scanResources("/locale");
			log.info("Found "+(scan.size()-1)+" locales");
			for(String item : scan){
				if(item.endsWith(".properties")){
					fromInternalFile(item.replace(".properties",""));
				}
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		changeLocale("en");
	}

	public void fromInternalFile(String name){
		String path = "locale/"+name+".properties";
		log.info("Loading locale '"+name+"' ("+path+")");
		try(InputStream stream = ResourceLoader.getResourceAsStream(path)){
			Localization locale = Localization.loadFromProps(stream);
			locales.put(locale.getId(), locale);
		}catch (IOException e){
			log.warn("Unable to load internal localization file '"+name+"'");
		}
	}
	/**
	 * @return Список известных id локалей
	 */
	public Set<String> getLocales() {
		return Collections.unmodifiableSet(locales.keySet());
	}

	/**
	 * Сменяет используемую локаль
	 * @param id id искомой локали
	 * @return true, если локаль сменена и false, если нет
	 */
	public boolean changeLocale(String id) {
		var foundLocale = locales.get(id);
		if (foundLocale == null) {
			log.error("Can't change locale to " + id);
			return false;
		}

		currentLocale = foundLocale;
		return true;
	}

	/**
	 * Возвращает локализованную или псевдолокализованную строку, соответствующую указанному ключу.
	 * @param key ключ.
	 * @return Переведённый текст.
	 */
	public String localize(String key) {
		return currentLocale.localize(key);
	}

	/**
	 * Пытается локализовать и форматировать строку.
	 * @param key ключ.
	 * @param args объекты для String.format
	 * @return Переведённый и отформатированный текст
	 */
	public String localize(String key, Object... args) {
		return currentLocale.localize(key, args);
	}
}
