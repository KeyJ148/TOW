package cc.abro.orchengine;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.init.Loader;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.init.interfaces.NetGameReadInterface;
import cc.abro.orchengine.init.interfaces.NetServerReadInterface;
import cc.abro.orchengine.init.interfaces.ServerInterface;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OrchEngine {

	public static void start() {
		builder().start();
	}

	public static void start(Set<String> activeProfiles, Set<String> packagesForScan) {
		builder()
				.addActiveProfiles(activeProfiles)
				.addPackagesForScan(packagesForScan)
				.start();
	}

	public static void start(GameInterface game, NetGameReadInterface netGameRead,
							 ServerInterface server, NetServerReadInterface netServerRead) {
		builder()
				.setGame(game)
				.setNetGameRead(netGameRead)
				.setServer(server)
				.setNetServerRead(netServerRead)
				.start();
	}

	public static void start(Class<? extends GameInterface> gameClass, Class<? extends NetGameReadInterface> netGameReadClass,
							 Class<? extends ServerInterface> serverClass, Class<? extends NetServerReadInterface> netServerReadClass) {
		builder()
				.setGameClass(gameClass)
				.setNetGameReadClass(netGameReadClass)
				.setServerClass(serverClass)
				.setNetServerReadClass(netServerReadClass)
				.start();
	}

	public static void start(GameInterface game, NetGameReadInterface netGameRead,
							 ServerInterface server, NetServerReadInterface netServerRead,
							 Class<? extends GameInterface> gameClass, Class<? extends NetGameReadInterface> netGameReadClass,
							 Class<? extends ServerInterface> serverClass, Class<? extends NetServerReadInterface> netServerReadClass,
							 Set<String> activeProfiles, Set<String> packagesForScan) {
		if (game != null && gameClass != null) {
			throw new IllegalArgumentException("GameInterface and Class<GameInterface> cannot be passed at the same time");
		}
		if (netGameRead != null && netGameReadClass != null) {
			throw new IllegalArgumentException("NetGameReadInterface and Class<NetGameReadInterface> cannot be passed at the same time");
		}
		if (server != null && serverClass != null) {
			throw new IllegalArgumentException("ServerInterface and Class<ServerInterface> cannot be passed at the same time");
		}
		if (netServerRead != null && netServerReadClass != null) {
			throw new IllegalArgumentException("NetServerReadInterface and Class<NetServerReadInterface> cannot be passed at the same time");
		}

		if (game != null) {
			Context.addService(game);
		}
		if (netGameRead != null) {
			Context.addService(netGameRead);
		}
		if (server != null) {
			Context.addService(server);
		}
		if (netServerRead != null) {
			Context.addService(netServerRead);
		}

		if (gameClass != null) {
			Context.addService(gameClass);
		}
		if (netGameReadClass != null) {
			Context.addService(netGameReadClass);
		}
		if (serverClass != null) {
			Context.addService(serverClass);
		}
		if (netServerReadClass != null) {
			Context.addService(netServerReadClass);
		}

		Set<String> packagesForScanWithEngine = new HashSet<>(packagesForScan);
		packagesForScanWithEngine.add(OrchEngine.class.getPackage().getName());
		new Loader().start(activeProfiles, packagesForScanWithEngine);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private GameInterface game;
		private NetGameReadInterface netGameRead;
		private ServerInterface server;
		private NetServerReadInterface netServerRead;

		private Class<? extends GameInterface> gameClass;
		private Class<? extends NetGameReadInterface> netGameReadClass;
		private Class<? extends ServerInterface> serverClass;
		private Class<? extends NetServerReadInterface> netServerReadClass;

		private final Set<String> activeProfiles = new HashSet<>();
		private final Set<String> packagesForScan = new HashSet<>();

		public Builder setGame(GameInterface game) {
			this.game = game;
			return this;
		}

		public Builder setNetGameRead(NetGameReadInterface netGameRead) {
			this.netGameRead = netGameRead;
			return this;
		}

		public Builder setServer(ServerInterface server) {
			this.server = server;
			return this;
		}

		public Builder setNetServerRead(NetServerReadInterface netServerRead) {
			this.netServerRead = netServerRead;
			return this;
		}

		public Builder setGameClass(Class<? extends GameInterface> gameClass) {
			this.gameClass = gameClass;
			return this;
		}

		public Builder setNetGameReadClass(Class<? extends NetGameReadInterface> netGameReadClass) {
			this.netGameReadClass = netGameReadClass;
			return this;
		}

		public Builder setServerClass(Class<? extends ServerInterface> serverClass) {
			this.serverClass = serverClass;
			return this;
		}

		public Builder setNetServerReadClass(Class<? extends NetServerReadInterface> netServerReadClass) {
			this.netServerReadClass = netServerReadClass;
			return this;
		}

		public Builder addActiveProfile(String activeProfile) {
			activeProfiles.add(activeProfile);
			return this;
		}

		public Builder addActiveProfiles(Collection<String> activeProfiles) {
			this.activeProfiles.addAll(activeProfiles);
			return this;
		}

		public Builder addPackageForScan(String packageForScan) {
			packagesForScan.add(packageForScan);
			return this;
		}

		public Builder addPackagesForScan(Collection<String> packagesForScan) {
			this.packagesForScan.addAll(packagesForScan);
			return this;
		}

		public void start() {
			OrchEngine.start(game, netGameRead, server, netServerRead,
					gameClass, netGameReadClass, serverClass, netServerReadClass,
					activeProfiles, packagesForScan);
		}
	}
}
