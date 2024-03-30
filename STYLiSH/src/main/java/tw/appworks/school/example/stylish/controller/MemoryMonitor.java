package tw.appworks.school.example.stylish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Controller
public class MemoryMonitor {

    @GetMapping("/memory/status")
    @ResponseBody
    public String checkStatus(){
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory Usage:");
        System.out.println("  Init: " + heapMemoryUsage.getInit());
        System.out.println("  Used: " + heapMemoryUsage.getUsed());
        System.out.println("  Committed: " + heapMemoryUsage.getCommitted());
        System.out.println("  Max: " + heapMemoryUsage.getMax());

        System.out.println(" Non-Heap Memory Usage:");
        System.out.println("  Init: " + nonHeapMemoryUsage.getInit());
        System.out.println("  Used: " + nonHeapMemoryUsage.getUsed());
        System.out.println("  Committed: " + nonHeapMemoryUsage.getCommitted());
        System.out.println("  Max: " + nonHeapMemoryUsage.getMax());

        return "Heap Memory Usage:\n" +
                "  Init: " + heapMemoryUsage.getInit() + "\n" +
                "  Used: " + heapMemoryUsage.getUsed() + "\n" +
                "  Committed: " + heapMemoryUsage.getCommitted() + "\n" +
                "  Max: " + heapMemoryUsage.getMax() + "\n" +
                "Non-Heap Memory Usage:\n" +
                "  Init: " + nonHeapMemoryUsage.getInit() + "\n" +
                "  Used: " + nonHeapMemoryUsage.getUsed() + "\n" +
                "  Committed: " + nonHeapMemoryUsage.getCommitted() + "\n" +
                "  Max: " + nonHeapMemoryUsage.getMax();
    }
}

