package trapx00.lightx00.client.bl.loginbl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.loginbl.factory.LoginBlFactory;
import trapx00.lightx00.client.blservice.loginblservice.FaceIdAuthenticationBlService;
import trapx00.lightx00.client.datafactory.logindataservicefactory.FaceIdAuthenticationDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.log.LogSeverity;

import java.rmi.RemoteException;

public class FaceIdAuthenticationBlController implements FaceIdAuthenticationBlService {
    private FaceIdAuthenticationDataService dataService = FaceIdAuthenticationDataServiceFactory.getService();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private LogService logService = LogServiceFactory.getLogService();

    /**
     * Login with image.
     *
     * @param bytes image in bytes
     * @return EmployeeVo if authenticated. null otherwise.
     */
    @Override
    public EmployeeVo authenticate(byte[] bytes) {
        try {
            String employeeId =  dataService.authenticate(bytes);
            if (employeeId == null) {
                return null;
            }
            EmployeeVo employeeVo = employeeInfo.queryById(employeeId);
            if (employeeVo != null) {
                logService.log(LogSeverity.Success, "使用人脸登录成功，登录成员ID：" + employeeVo.getId());
            } else {
                logService.log(LogSeverity.Failure, "使用人脸登录失败。");
            }
            LoginBlFactory.getController().setCurrentUser(employeeVo); //to make current user service available
            return employeeVo;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "使用人脸登录失败，原因是网络原因，信息：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }
}
