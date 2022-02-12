package cc.abro.tow.services;

import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.cycle.Update;
import cc.abro.orchengine.init.Finalizer;

import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_NOT_SHUTDOWN;

@TestService({TEST_NOT_SHUTDOWN})
public class TestEngine extends Engine {

    private final Finalizer finalizer;

    public TestEngine(Update update, Render render, Analyzer analyzer, Finalizer finalizer) {
        super(update, render, analyzer);
        this.finalizer = finalizer;
    }

    @Override
    public void stoppingCallback() {
        finalizer.stopServicesAndCloseResources();
    }
}
