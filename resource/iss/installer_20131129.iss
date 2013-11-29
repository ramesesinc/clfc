;DefaultGroupName=My Program

[Setup]
AppName=CLFC
AppVersion=1.0
DefaultDirName={pf}\Rameses Systems Inc.\CLFC
Compression=lzma2
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: "platform\clfc_platform\*.*"; DestDir: "{app}"
Source: "platform\clfc_platform\bin\*.*"; DestDir: "{app}\bin"

Source: "platform\clfc_platform\clfc_platform\*.*"; DestDir: "{app}\clfc_platform"
Source: "platform\clfc_platform\clfc_platform\config\Modules\*.*"; DestDir: "{app}\clfc_platform\config\Modules"
Source: "platform\clfc_platform\clfc_platform\core\locale\*.*"; DestDir: "{app}\clfc_platform\core\locale"
Source: "platform\clfc_platform\clfc_platform\modules\*.*"; DestDir: "{app}\clfc_platform\modules"
Source: "platform\clfc_platform\clfc_platform\modules\ext\*.*"; DestDir: "{app}\clfc_platform\modules\ext"
Source: "platform\clfc_platform\clfc_platform\modules\locale\*.*"; DestDir: "{app}\clfc_platform\modules\locale"
Source: "platform\clfc_platform\clfc_platform\update_tracking\*.*"; DestDir: "{app}\clfc_platform\update_tracking"

Source: "platform\clfc_platform\etc\*.*"; DestDir: "{app}\etc"

Source: "platform\clfc_platform\platform6\*.*"; DestDir: "{app}\platform6"
Source: "platform\clfc_platform\platform6\config\ModuleAutoDeps\*.*"; DestDir: "{app}\platform6\config\ModuleAutoDeps"
Source: "platform\clfc_platform\platform6\config\Modules\*.*"; DestDir: "{app}\platform6\config\Modules"
Source: "platform\clfc_platform\platform6\core\*.*"; DestDir: "{app}\platform6\core"
Source: "platform\clfc_platform\platform6\core\locale\*.*"; DestDir: "{app}\platform6\core\locale"
Source: "platform\clfc_platform\platform6\docs\*.*"; DestDir: "{app}\platform6\docs"
Source: "platform\clfc_platform\platform6\lib\*.*"; DestDir: "{app}\platform6\lib"
Source: "platform\clfc_platform\platform6\lib\locale\*.*"; DestDir: "{app}\platform6\lib\locale"
Source: "platform\clfc_platform\platform6\modules\*.*"; DestDir: "{app}\platform6\modules"
Source: "platform\clfc_platform\platform6\modules\ext\*.*"; DestDir: "{app}\platform6\modules\ext"
Source: "platform\clfc_platform\platform6\modules\locale\*.*"; DestDir: "{app}\platform6\modules\locale"
Source: "platform\clfc_platform\platform6\update_tracking\*.*"; DestDir: "{app}\platform6\update_tracking"

[Icons]
Name: "{commondesktop}\CLFC 2"; Filename: "{app}\bin\clfc_platform.exe"; IconFilename: "{app}\bin\shortcut.ico"
Name: "{commonstartmenu}\CLFC 2"; Filename: "{app}\bin\clfc_platform.exe"; IconFilename: "{app}\bin\shortcut.ico"

[Code]
#include <it_download.iss>
#define MinJRE "1.6"
#define WebJRE "http://download688.mediafire.com/9bsdegusw5ug/bni6z8l8zt3wiwz/jre-6u45-windows-i586.exe"

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
    Log('{#WebJRE}');
    ITD_AddFile('{#WebJRE}', ExpandConstant('{tmp}\jre_1.6.exe'));
    ITD_DownloadAfter(wpReady); 
  end;
end;

procedure CurStepChanged(CurStep: TSetupStep);
var
  ResultCode: Integer;
begin
  if CurStep=ssInstall then begin //Lets install those files that were downloaded for us
    Exec(ExpandConstant('{tmp}\jre_1.6.exe'), '', '', SW_SHOW, ewWaitUntilTerminated, ResultCode);
  end;
end;