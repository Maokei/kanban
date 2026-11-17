package se.maokei.kanban;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KanbanApplicationTests extends AbstractContainerBaseTest {

	@Autowired
	private AsyncTaskExecutor applicationTaskExecutor;

	@Test
	public void contextLoads() throws Exception {
		boolean isVirtual = applicationTaskExecutor.submit(() -> Thread.currentThread().isVirtual()).get();
		assertTrue(isVirtual, "Application task executor should execute tasks on virtual threads");
	}

}
