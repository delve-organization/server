package com.github.delve.integrationtest.util.basedata;

import org.springframework.test.context.TestContext;

import java.util.HashSet;
import java.util.Set;

import static com.github.delve.integrationtest.util.AnnotationUtil.findAnnotationOnMethod;

public class PreloadTestExecutionListener extends BaseDataTestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) {
        final Preload useBaseDataAnnotation = findAnnotationOnMethod(testContext.getTestMethod(), Preload.class);
        if (useBaseDataAnnotation == null) {
            return;
        }

        final Set<Class<? extends BaseData>> loadedBaseDataClasses = new HashSet<>();
        for (Class<? extends BaseData> baseDataClass : useBaseDataAnnotation.value()) {
            getBeanAndLoad(baseDataClass, loadedBaseDataClasses);
        }
    }

    @Override
    public void afterTestMethod(final TestContext testContext) {
        final Preload preloadAnnotation = findAnnotationOnMethod(testContext.getTestMethod(), Preload.class);
        if (preloadAnnotation == null) {
            return;
        }

        final Set<Class<? extends BaseData>> unloadedBaseDataClasses = new HashSet<>();
        for (Class<? extends BaseData> baseDataClass : preloadAnnotation.value()) {
            getBeanAndUnload(baseDataClass, unloadedBaseDataClasses);
        }
    }
}
