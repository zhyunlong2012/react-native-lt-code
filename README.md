# react-native-lt-code

## Getting started

`$ npm install react-native-lt-code --save`

### Mostly automatic installation

`$ react-native link react-native-lt-code`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-lt-code` and add `LtCode.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libLtCode.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.LtCodePackage;` to the imports at the top of the file
  - Add `new LtCodePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-lt-code'
  	project(':react-native-lt-code').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-lt-code/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-lt-code')
  	```


## Usage
```javascript
import LtCode from 'react-native-lt-code';

// TODO: What to do with the module?
LtCode;
```
