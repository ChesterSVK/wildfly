package org.jboss.as.test.integration.jsr77;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.management.j2ee.Management;
import javax.management.j2ee.ManagementHome;

/**
 * @author jcibik
 */
@RunWith(Arquillian.class)
public class JsrTestCase {



    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(org.jboss.as.test.integration.jsr77.JsrTestCase.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    //Test for simple MBean functionality
    @Test
    public void testSimple(){
        try {
            Context ic = new InitialContext();
            Object obj = ic.lookup("ejb/mgmt/MEJB");
            ManagementHome mejbHome = (ManagementHome)obj;

            final Management management = mejbHome.create();
            Assert.assertNotNull(management.getDefaultDomain());
            Assert.assertTrue(management.getMBeanCount() > 0);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
    }


}