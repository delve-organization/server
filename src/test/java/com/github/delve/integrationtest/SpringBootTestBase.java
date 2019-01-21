package com.github.delve.integrationtest;

import com.github.delve.integrationtest.node.util.NodeBaseData;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.treecard.util.TreeCardBaseData;
import com.github.delve.integrationtest.user.util.RoleBaseData;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.AuthenticateTestExecutionListener;
import com.github.delve.integrationtest.util.basedata.PreloadTestExecutionListener;
import com.github.delve.integrationtest.util.context.ContextProvider;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.util.basedata.BaseDataTestExecutionListener;
import com.github.delve.security.service.jwt.JwtProvider;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        listeners = {
                PreloadTestExecutionListener.class,
                AuthenticateTestExecutionListener.class,
                BaseDataTestExecutionListener.class
        }
)
public abstract class SpringBootTestBase {

    @TestConfiguration
    static class SpringBootTestContextConfiguration {

        @Bean
        public NodeBaseData nodeBaseData() {
            return new NodeBaseData();
        }

        @Bean
        public TreeBaseData treeBaseData() {
            return new TreeBaseData();
        }

        @Bean
        public UserBaseData userBaseData() {
            return new UserBaseData();
        }

        @Bean
        public RoleBaseData roleBaseData() {
            return new RoleBaseData();
        }

        @Bean
        public TreeCardBaseData treeCardBaseData() {
            return new TreeCardBaseData();
        }

        @Bean
        public ContextProvider contextProvider() {
            return new ContextProvider();
        }

        @Bean
        public JwtAuthenticator jwtAuthenticator(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider) {
            return new JwtAuthenticator(authenticationManager, jwtProvider);
        }
    }
}
