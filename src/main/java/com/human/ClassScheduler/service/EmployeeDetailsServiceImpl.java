package com.human.ClassScheduler.service;

import com.human.ClassScheduler.model.EmployeeDetails;
import com.human.ClassScheduler.model.Group;
import com.human.ClassScheduler.repository.EmployeeDetailsRepository;
import com.human.ClassScheduler.repository.GroupRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<EmployeeDetails> createEmployeeGroups(MultipartFile file) {
        List<EmployeeDetails> salesList = new ArrayList<>();
        try {

            org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file.getInputStream());
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            Integer id = -1, nm = -1, lv = -1;

            List<Long> idList = new ArrayList<>();
            List<String> nmList = new ArrayList<>();
            List<String> lvList = new ArrayList<>();
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext())
            {
                org.apache.poi.ss.usermodel.Row row = rowIterator.next();

                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if(cell.getRowIndex()==0){
                        if (cell.getStringCellValue().trim().equalsIgnoreCase("Emp ID")) {
                            id = cell.getColumnIndex();
                            System.err.println(id);
                        }
                        if (cell.getStringCellValue().trim().equalsIgnoreCase("Emp Name")) {
                            // loanNo
                            nm = cell.getColumnIndex();
                            System.err.println(nm);
                        }
                        if (cell.getStringCellValue().trim().equalsIgnoreCase("Nihongo Level")) {
                            lv = cell.getColumnIndex();
                            System.err.println(lv);
                        }

                    } else {
                        if (cell.getColumnIndex() == id) {
                            idList.add((long)cell.getNumericCellValue());
                        }
                        if (cell.getColumnIndex() == nm) {
                            nmList.add(cell.getStringCellValue().replace('\'', ' ').trim());
                        }
                        if (cell.getColumnIndex() == lv) {
                            lvList.add(cell.getStringCellValue().trim());
                        }

                    }
                    //String cellValue = dataFormatter.formatCellValue(cell);
                    //System.out.print(cellValue + "\t");
                }
                //System.out.println();
            }

            // update nachStatusLoan Table
//            Set<EmployeeDetails> empDtlsSet = new HashSet<EmployeeDetails>();
//            Set<String> oSet = new HashSet<>();



            for (Integer k = 0; k < idList.size(); k++) {
                EmployeeDetails empDtls = new EmployeeDetails();
                empDtls.setEmpId(idList.get(k));
                empDtls.setEmployeeName(nmList.get(k));
                empDtls.setNihongoLevel(lvList.get(k));

                salesList.add(empDtls);
            }

            salesList.sort(Comparator.comparing(EmployeeDetails::getEmployeeName));
            salesList.forEach(sale-> System.out.println(sale.getEmpId()+"-->"+sale.getEmployeeName()));
            groupCreationAndDataSave(salesList);
        }catch (Exception e){
            e.printStackTrace();
        }

        return salesList;
    }

    private void groupCreationAndDataSave(List<EmployeeDetails> empList)
    {
        int count=0;
        Group grp = new Group();
        List<EmployeeDetails> employees=new ArrayList<>();
        List<EmployeeDetails> diffList = new ArrayList<>(empList);
        Collections.copy(diffList,empList);
          for(EmployeeDetails emp:empList)
        {
            if(count==0) {

                grp = groupService.createGroup(grp);

            }
            emp.setGroup(grp);
            employees.add(emp);
            count++;
            if(count>0&&count%8==0)
            {
                employeeDetailsRepository.saveAll(employees);
                grp.setTeamCount(employees.size());
                groupRepository.save(grp);
                diffList.removeAll(employees);
                employees.clear();
                count=0;
            }
            if(diffList.size()<=7)
            {
                Group group = new Group();
                Group addedGroup = groupRepository.save(group);
                diffList.forEach(e->e.setGroup(addedGroup));
                employeeDetailsRepository.saveAll(diffList);
                addedGroup.setTeamCount(diffList.size());
                groupRepository.updateTeamCount(diffList.size(),addedGroup.getGroupId());


            }
        }

    }
}
