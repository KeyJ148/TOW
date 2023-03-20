package cc.abro.orchengine.resources.locales;

import cc.abro.orchengine.context.EngineService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.*;

/**
 * Сервис локализации. Пока что не умеет в загрузку локалей из файла, но переводики можно вписать в DEFAULT_LOCALE.<br>
 * Чекай закономерность для {@link LocalizationService#DEFAULT_LOCALE}:<br><br>
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
	 * Временная захардкоженная локализация в формате properties.
	 * Наличие значения locale.id <b>ОБЯЗАТЕЛЬНО</b>
	 */
	private static final String DEFAULT_LOCALE = """
			locale.id = ru
			locale.name = Russian (русский)
			sample.text = SAMPLE_TEXT
			sample.format = SAMPLE{0}TEXT
			""";
	private final Map<String, Localization> locales = new HashMap<>();
	private Localization currentLocale = Localization.loadFromProps(DEFAULT_LOCALE); // Костыль.

	// вызови меня полностью.
	public LocalizationService() {
		locales.put(currentLocale.getId(), currentLocale); // продолжение костыля.
		// Где-то тут должны сканироваться и собираться локали. Потом прикрутим, главное шоб работало.
	}

	/**
	 * @return Список известных id локалей
	 */
	public List<String> getLocales() {
		return locales.keySet().stream().toList();
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
