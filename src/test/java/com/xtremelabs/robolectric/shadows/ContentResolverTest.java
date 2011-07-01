package com.xtremelabs.robolectric.shadows;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import com.xtremelabs.robolectric.WithTestDefaultsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(WithTestDefaultsRunner.class)
public class ContentResolverTest {
    @Test
    public void query_shouldReturnACursor() throws Exception {
        ContentResolver contentResolver = new Activity().getContentResolver();
        Cursor cursor = contentResolver.query(null, null, null, null, null);
        assertNotNull(cursor);
    }
}
