import { NativeModules } from 'react-native';
// import { disconnect } from 'react-native-my-ble-manager';

const { LtCode } = NativeModules;

// export default LtCode;
export default {
    giveme(code){
        let res = LtCode.scanCode(code);
        return res;
        console.log('react 解密接口')
    },

    getConnect(Macid){
        console.log('开始连接蓝牙设备');
        
        // console.log(LtCode.getConnect(Macid));
        // return 'aaa';
        // return true;
        let res = LtCode.getConnect(Macid);
        console.log('连接蓝牙返回结果' + res)
        return res;
    },

    connect(peripheralId) {
        return new Promise((fulfill, reject) => {
          LtCode.getConnect(peripheralId, (error) => {
            if (error) {
              reject(error);
            } else {
              fulfill();
            }
          });
        });
      },

    disconnect(){
        console.log('关闭蓝牙模块')
        LtCode.disConnect();
    },

    printTest(Macid,text){
        console.log('测试打印页(模块内11)'+text.toString());

        LtCode.SDKDemo(Macid,text.toString());
    },

    printLtLabel(SelectedBDAddress,products,num,user,qrcode,desc,customer){
      console.log("打印辽弹标签");
      LtCode.printLtLabel(SelectedBDAddress,products,num,user,qrcode,desc,customer);
      // LtCode.printLtLabel("00:03:84:26:49:73","products","num","user","qrcode");
    },

    sendText(Macid,text){
        console.log('测试打印页(模块内221)'+text.toString());

        LtCode.sendText(Macid,text.toString());
    },

    CPCLDemo(Macid){
        console.log('测试打印页(模块内221)');

        LtCode.CPCLDemo(Macid);
    },

    checkBt(){
        console.log('检测蓝牙信息');
        LtCode.ListBluetoothDevice();
    },

    connetBluetoothDevice(){
        console.log('返回匹配的蓝牙设备信息lt index');
        return new Promise((fulfill, reject) => {
            LtCode.connetBluetoothDevice((data) => {
              console.log(data)
              fulfill(data);
            });
          });
    },

    myBtState(){
        console.log('返回当前蓝牙状态lt index');
        let res =  LtCode.myBtState();
        console.log('返回当前蓝牙状态lt index 结果');
        console.log(res);
        return res;
    }

}