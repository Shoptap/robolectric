package com.xtremelabs.robolectric.res;


import com.xtremelabs.robolectric.R;
import org.junit.Test;

import java.io.File;

import static com.xtremelabs.robolectric.util.TestUtil.resourceFile;
import static org.junit.Assert.*;

public class ResourceLoaderTest {
    @Test
    public void shouldUseFileSystemSeparatorWhenEvaluatingLayoutDirectories() throws Exception {
        assertTrue(ResourceLoader.isLayoutDirectory(File.separator + "layout"));
    }

    @Test
    public void shouldLoadSystemResources() throws Exception {
        ResourceLoader resourceLoader = new ResourceLoader(10, R.class, resourceFile("res"), resourceFile("assets"));
        String stringValue = resourceLoader.getStringValue(android.R.string.copy);
        assertEquals("Copy", stringValue);

        ViewLoader.ViewNode node = resourceLoader.getLayoutViewNode("android:layout/simple_spinner_item");
        assertNotNull(node);
    }

    @Test
    public void shouldLoadLocalResources() throws Exception {
        ResourceLoader resourceLoader = new ResourceLoader(10, R.class, resourceFile("res"), resourceFile("assets"));
        String stringValue = resourceLoader.getStringValue(R.string.copy);
        assertEquals("Local Copy", stringValue);
    }
}
