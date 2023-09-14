/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store.control;

/**
 *
 * @author andrelima
 */
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExecutorServiceProducer {

    @Resource
    private ManagedExecutorService executorService;

    public ManagedExecutorService getExecutorService() {
        return executorService;
    }
}