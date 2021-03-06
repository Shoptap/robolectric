package com.xtremelabs.robolectric.util;

import com.xtremelabs.robolectric.WithTestDefaultsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(WithTestDefaultsRunner.class)
public class H2Map_TypeForwardOnlyTest {

	H2Map_TypeForwardOnly map;
	@Before
    public void setUp() throws Exception {
      map = new H2Map_TypeForwardOnly();  
    }

   
    
    @Test
    public void testGetH2ResultSetIs_TYPE_FORWARD_ONLY() throws SQLException {   	
    	assertThat(map.getResultSetType(),equalTo(ResultSet.TYPE_FORWARD_ONLY));
    }
}
