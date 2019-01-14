package com.github.delve.integrationtest.util.basedata;

import com.github.delve.integrationtest.util.AnnotationUtil;
import com.github.delve.integrationtest.util.context.ContextProvider;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.HashSet;
import java.util.Set;

public class BaseDataTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) {
        final UseBaseData useBaseDataAnnotation = AnnotationUtil.findAnnotationOnMethod(testContext.getTestMethod(), UseBaseData.class);
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
        final UseBaseData useBaseDataAnnotation = AnnotationUtil.findAnnotationOnMethod(testContext.getTestMethod(), UseBaseData.class);
        if (useBaseDataAnnotation == null) {
            return;
        }

        final Set<Class<? extends BaseData>> unloadedBaseDataClasses = new HashSet<>();
        for (Class<? extends BaseData> baseDataClass : useBaseDataAnnotation.value()) {
            getBeanAndUnload(baseDataClass, unloadedBaseDataClasses);
        }
    }

    protected void getBeanAndLoad(final Class<? extends BaseData> baseDataClass, final Set<Class<? extends BaseData>> loadedBaseDataClasses) {
        if (loadedBaseDataClasses.contains(baseDataClass)) {
            return;
        }

        final BaseData baseData = ContextProvider.getBean(baseDataClass);
        if (baseData.dependsOn() != null) {
            getBeanAndLoad(baseData.dependsOn(), loadedBaseDataClasses);
        }

        baseData.load();
        loadedBaseDataClasses.add(baseDataClass);
    }

    protected void getBeanAndUnload(final Class<? extends BaseData> baseDataClass, final Set<Class<? extends BaseData>> unloadedBaseDataClasses) {
        if (unloadedBaseDataClasses.contains(baseDataClass)) {
            return;
        }

        final BaseData baseData = ContextProvider.getBean(baseDataClass);
        baseData.unload();
        unloadedBaseDataClasses.add(baseDataClass);

        if (baseData.dependsOn() != null) {
            getBeanAndUnload(baseData.dependsOn(), unloadedBaseDataClasses);
        }
    }
}
