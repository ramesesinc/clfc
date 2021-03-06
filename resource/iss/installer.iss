;DefaultGroupName=My Program

[Setup]
AppName=CLFC
AppVersion=2.0
AppPublisher=Rameses Systems, Inc.
DefaultDirName=C:\CLFC
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: "platform\clfc2_platform_thin\*.*"; DestDir: "{app}"
Source: "platform\clfc2_platform_thin\lib\*.*"; DestDir: "{app}\lib"

[Icons]
Name: "{commondesktop}\CLFC 2"; Filename: "{app}\clfc2_platform.bat"; IconFilename: "{app}\shortcut.ico";
Name: "{commonstartmenu}\CLFC 2"; Filename: "{app}\clfc2_platform.bat"; IconFilename: "{app}\shortcut.ico";

[Run]
Filename: "{app}\clfc2_platform.bat"; Description: "Launch Application"; Flags: postinstall nowait skipifsilent;

[Code]
#include <it_download.iss>
#define MinJRE "1.6"
#define WebJRE64 "http://dl.filehorse.com/win/browsers-and-plugins/java-runtime-64/Java-Runtime-Environment-1.6.0.45-(64-bit).exe?st=4JME8hKSPEd34kWqMamJiQ&e=1386057111&fn=jre-6u45-windows-x64.exe"
#define WebJRE32 "http://dl.filehorse.com/win/browsers-and-plugins/java-runtime-32/Java-Runtime-Environment-1.6.0.45-(32-bit).exe?st=jCyKWxEigOuyJui5yGtO7A&e=1386056774&fn=jre-6u45-windows-i586.exe"

function IsJREInstalled: Boolean;
var
  JREVersion: string;
begin
  // read JRE version
  Result := RegQueryStringValue(HKLM32, 'Software\JavaSoft\Java Runtime Environment', 'CurrentVersion', JREVersion);
  // if the previous reading failed and we're on 64-bit Windows, try to read 
  // the JRE version from WOW node
  if not Result and IsWin64 then
    Result := RegQueryStringValue(HKLM64, 'Software\JavaSoft\Java Runtime Environment', 'CurrentVersion', JREVersion);
  // if the JRE version was read, check if it's at least the minimum one 
  if Result then
    Result := CompareStr(JREVersion, '{#MinJRE}') >= 0;
end;

procedure InitializeWizard();
begin
  ITD_Init();
  // check if JRE is installed; if not, then...
  if not IsJREInstalled then
  begin
    //ITD_AddFile('http://download.oracle.com/otn-pub/java/jdk/6u45-b06/jre-6u45-windows-i586.exe', ExpandConstant('{tmp}\jdk_1.5.exe'));
    //Log('{#WebJRE}');
    if isWin64() = True then
      ITD_AddFile('{#WebJRE64}', ExpandConstant('{tmp}\jre_1.6_64.exe'));
    if isWin64 = False then
      ITD_AddFile('{#WebJRE32}', ExpandConstant('{tmp}\jre_1.6_32.exe'));
    ITD_DownloadAfter(wpReady); 
  end;
end;

procedure CurStepChanged(CurStep: TSetupStep);
var
  ResultCode: Integer;
begin
  if CurStep=ssInstall then begin //Lets install those files that were downloaded for us
    if isWin64() = True then
      Exec(ExpandConstant('{tmp}\jre_1.6_64.exe'), '', '', SW_SHOW, ewWaitUntilTerminated, ResultCode);
    if isWin64() = False then
      Exec(ExpandConstant('{tmp}\jre_1.6_32.exe'), '', '', SW_SHOW, ewWaitUntilTerminated, ResultCode);
  end;
end;